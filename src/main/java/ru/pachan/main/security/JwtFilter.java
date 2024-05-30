package ru.pachan.main.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.util.RequestLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final RequestProvider requestProvider;
    private final String adminUsername;
    private final String adminPassword;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);


        if (request.getRequestURI().startsWith("/actuator")) {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            String username = "";
            String password = "";

            if (authHeader != null && authHeader.startsWith("Basic ")) {
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
                String[] loginAndPassword = credentials.split(":", 2);
                username = loginAndPassword[0];
                password = loginAndPassword[1];
            }
            if (Objects.equals(username, adminUsername) && Objects.equals(password, adminPassword)) {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        null, null, Collections.singletonList(
                        new SimpleGrantedAuthority("ActuatorAdmin")
                )));
            } else {
                RequestLogger.writeSlf4jLog(requestWrapper, responseWrapper, requestProvider, UNAUTHORIZED.getReasonPhrase());
                response.sendError(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase());
                return;
            }
        } else {
            try {
                String token = requestProvider.resolveToken(request);
                if (requestProvider.validateToken(token)) {
                    requestProvider.adminCheck(token, request);
                    requestProvider.checkPermission(token, request);
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            null, null, Collections.singletonList(
                            new SimpleGrantedAuthority("VerifiedToken")
                    )));
                }
            } catch (RequestException e) {
                SecurityContextHolder.clearContext();
                response.sendError(e.getHttpStatus().value(), e.getMessage());
                RequestLogger.writeSlf4jLog(requestWrapper, responseWrapper, requestProvider, e.getMessage());
                return;
            }
        }
        filterChain.doFilter(requestWrapper, responseWrapper);
        RequestLogger.writeSlf4jLog(requestWrapper, responseWrapper, requestProvider, "");
    }
}
