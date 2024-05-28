package ru.pachan.main.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.repository.auth.UserRepository;
import ru.pachan.main.util.refs.auth.user.RoleRefEnum;

import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static ru.pachan.main.util.auth.TokenSearcher.getPayload;
import static ru.pachan.main.util.enums.ExceptionEnum.*;
import static ru.pachan.main.util.refs.auth.user.RoleRefEnum.ADMIN;

@Component
public class RequestProvider {

    @Value("${jwt.key}")
    String secretKey;

    private final UserRepository userRepository;

    public RequestProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    Boolean validateToken(String token) throws RequestException {
        if (token == null || token.isBlank()) {
            throw new RequestException(EXPIRED_OR_INVALID_TOKEN.getMessage(), UNAUTHORIZED);
        }
        try {
            JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new RequestException(EXPIRED_OR_INVALID_TOKEN.getMessage(), UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            throw new RequestException(SYSTEM_ERROR.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }

    void checkPermission(String token, HttpServletRequest httpServletRequest) throws RequestException {
        if (token == null) {
            throw new RequestException(INVALID_TOKEN.getMessage(), FORBIDDEN);
        }
        List<String> path = Arrays.stream(httpServletRequest.getRequestURI().split("/")).filter(it -> !it.isBlank()).toList();
        if (Objects.equals(path.get(2), "refresh")) {
            return;
        }
        Map<String, Short> permission = getPermission(token);
        // TODO сделать PUT
        try {
            if (Objects.equals(httpServletRequest.getMethod(), HttpMethod.GET.name())) {
                if ((path.size() == 3 || path.size() == 4) && permission.get(path.get(2)) < 1) {
                    throw new RequestException(PERMISSION_DENIED.getMessage(), FORBIDDEN);
                }
            } else if (Objects.equals(httpServletRequest.getMethod(), HttpMethod.POST.name())) {
                if (path.size() == 3 && permission.get(path.get(2)) < 2) {
                    throw new RequestException(PERMISSION_DENIED.getMessage(), FORBIDDEN);
                }
            } else if (Objects.equals(httpServletRequest.getMethod(), HttpMethod.DELETE.name())) {
                if (permission.get(path.get(2)) < 4) {
                    throw new RequestException(PERMISSION_DENIED.getMessage(), FORBIDDEN);
                }
            }
        } catch (NullPointerException e) {
            throw new RequestException(INVALID_TOKEN.getMessage(), FORBIDDEN);
        }
    }

    Map<String, Short> getPermission(String token) throws RequestException {
        String payload = getPayload(token);

        try {
            return RoleRefEnum.getPermissionsByRoleId(new ObjectMapper().readTree(payload).get("roleId").shortValue());
        } catch (Throwable e) {
            throw new RequestException(SYSTEM_ERROR.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }

    void adminCheck(String token, HttpServletRequest httpServletRequest) throws RequestException, JsonProcessingException {
        List<String> path = Arrays.stream(httpServletRequest.getRequestURI().split("/")).filter(it -> !it.isBlank()).toList();
        if (!Objects.equals(path.get(1), "auth")) return;

        String payload = getPayload(token);

        long userId = 0L;

        try {
            userId = new ObjectMapper().readTree(payload).get("userId").asLong();
        } catch (NumberFormatException e) {
            throw new RequestException(EXPIRED_OR_INVALID_TOKEN.getMessage(), UNAUTHORIZED);
        } catch (Throwable e) {
            throw new RequestException(SYSTEM_ERROR.getMessage(), INTERNAL_SERVER_ERROR);
        }

        try {
            if (Objects.equals(httpServletRequest.getMethod(), HttpMethod.GET.name()) ||
                    Objects.equals(httpServletRequest.getMethod(), HttpMethod.POST.name()) ||
                    Objects.equals(httpServletRequest.getMethod(), HttpMethod.PUT.name())) {
                if (path.size() == 4 && (Objects.equals(path.get(2), "user") || Objects.equals(path.get(2), "refresh"))
                        || path.size() == 3 && Objects.equals(path.get(2), "refresh")
                ) return;
            }
        } catch (NullPointerException e) {
            throw new RequestException(INVALID_PATH.getMessage(), INTERNAL_SERVER_ERROR);
        }

        if (userRepository.findById(userId).orElseThrow(() ->
                new RequestException(USER_IS_MISSING.getMessage(), UNAUTHORIZED)
        ).getRoleId() != ADMIN.getRole().id()
        ) throw new RequestException(PERMISSION_DENIED.getMessage(), FORBIDDEN);

    }



}
