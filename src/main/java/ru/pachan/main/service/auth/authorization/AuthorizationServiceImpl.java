package ru.pachan.main.service.auth.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pachan.main.dto.auth.AuthorizationDto;
import ru.pachan.main.dto.auth.RefreshDataDto;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.auth.RefreshToken;
import ru.pachan.main.model.auth.User;
import ru.pachan.main.repository.auth.RefreshTokenRepository;
import ru.pachan.main.repository.auth.UserRepository;
import ru.pachan.main.util.auth.TokenSearcher;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;
import static ru.pachan.main.util.enums.ExceptionEnum.*;

@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Value("${jwt.refresh-token-expiration}")
    private String refreshTime;

    @Value("${jwt.access-token-expiration}")
    private String accessTime;

    @Value("${jwt.key}")
    private String key;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenSearcher tokenSearcher;

    public RefreshDataDto generate(AuthorizationDto authorizationDto) throws RequestException {
        User user = validateUser("",
                userRepository.findByLogin(authorizationDto.login()).orElseThrow(() ->
                        new RequestException(
                                OBJECT_NOT_FOUND.getMessage(),
                                GONE
                        ))
        );
        // TODO переделать на новое
        if (new BCryptPasswordEncoder().matches(authorizationDto.password(), user.getPassword())) {
            return generateJWT(user, null);
        } else {
            throw new RequestException(
                    WRONG_LOGIN_OR_PASSWORD.getMessage(),
                    BAD_REQUEST
            );
        }
    }

    public RefreshDataDto refresh(String token) throws RequestException {
        return generateJWT(null, token);
    }

    private RefreshDataDto generateJWT(User user, String token) throws RequestException {
        RefreshToken refreshEntry = new RefreshToken();
        try {
            if (token != null && !token.isBlank()) {
                refreshEntry = refreshTokenRepository.findByRefreshToken(token.split(" ")[1]).orElseThrow(NullPointerException::new);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException ignored) {
        } catch (Throwable e) {
            throw new RequestException(EXPIRED_OR_INVALID_TOKEN.getMessage(), UNAUTHORIZED);
        }

        User foundUser;
        if (user == null) {
            foundUser = validateUser(token, null);
        } else {
            foundUser = user;
        }

        // ATTENTION понять почему не надо
        String refreshToken = "";

        // TODO сделать что-то с NULL
        try {
            refreshToken = JWT.create()
                    .withClaim("userId", foundUser.getId())
                    .withIssuedAt(new Date())
                    // TODO переделать депрекейтед
                    .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(refreshTime))).sign(
                            Algorithm.HMAC256(key)
                    );
        } catch (NumberFormatException e) {
            throw new RequestException(INVALID_PROPERTY_TOKEN_ACCESS_TIME.getMessage(), INTERNAL_SERVER_ERROR);
        } catch (Throwable e) {
            throw new RequestException(DENIED_TOKEN_CREATE.getMessage(), INTERNAL_SERVER_ERROR);
        }

        if (refreshEntry.getRefreshToken() != null && !refreshEntry.getRefreshToken().isBlank()) {
            try {
                if (JWT.decode(refreshEntry.getRefreshToken()).getExpiresAt().getTime() < new Date(System.currentTimeMillis()).getTime()) {
                    throw new RequestException(EXPIRED_OR_INVALID_TOKEN.getMessage(), UNAUTHORIZED);
                } else {
                    refreshEntry.setRefreshToken(refreshToken);
                }
            } catch (NumberFormatException e) {
                throw new RequestException(INVALID_PROPERTY_TOKEN_REFRESH_TIME.getMessage(), INTERNAL_SERVER_ERROR);
            } catch (Throwable e) {
                throw new RequestException(INCORRECT_EXPIRATION_DATE.getMessage(), UNAUTHORIZED);
            }
        } else {
            refreshEntry.setRefreshToken(refreshToken);
        }

        String newToken = "";

        try {
            newToken = JWT.create()
                    .withClaim("userId", foundUser.getId())
                    .withClaim("roleId", Integer.valueOf(foundUser.getRoleId()))
                    .withIssuedAt(new Date())
                    // TODO переделать депрекейтед
                    .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(accessTime))).sign(
                            Algorithm.HMAC256(key)
                    );
        } catch (NumberFormatException e) {
            throw new RequestException(INVALID_PROPERTY_TOKEN_ACCESS_TIME.getMessage(), INTERNAL_SERVER_ERROR);
        } catch (Throwable e) {
            throw new RequestException(DENIED_TOKEN_CREATE.getMessage(), INTERNAL_SERVER_ERROR);
        }

        RefreshToken userRefreshToken = foundUser.getRefreshToken();
        userRefreshToken.setRefreshToken(refreshEntry.getRefreshToken());
        refreshTokenRepository.save(userRefreshToken);

        // ATTENTION Мб переделать
        if (user != null) {
            return new RefreshDataDto(userRefreshToken.getRefreshToken(), newToken, user.getRoleId(), user.getRoleId());

        } else {
            return new RefreshDataDto(userRefreshToken.getRefreshToken(), newToken, 0, 0);

        }


    }

    private User validateUser(String token, User user) throws RequestException {
        // ATTENTION Мб переделать на перегрузку
        String localToken = token == null ? "" : token;
        if (user == null) {
            return userRepository.findById(Long.parseLong(tokenSearcher.getPayloadField(localToken, "userId"))).orElseThrow(() ->
                    new RequestException(USER_IS_MISSING.getMessage(), UNAUTHORIZED));
        }
        return user;
    }

//    IDE - Бэд практис.
//    private User validateUser(String token, Optional<User> user) throws RequestException {
//        return user.orElse(
//                userRepository.findById(Long.parseLong(tokenSearcher.getPayloadField(token, "userId"))).orElseThrow(() ->
//                        new RequestException(USER_IS_MISSING.getMessage(), UNAUTHORIZED))
//        );
//    }

}
