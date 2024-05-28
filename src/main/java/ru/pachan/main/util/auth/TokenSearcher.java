package ru.pachan.main.util.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.repository.auth.UserRepository;

import java.util.Base64;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static ru.pachan.main.util.enums.ExceptionEnum.*;
import static ru.pachan.main.util.refs.auth.user.RoleRefEnum.ADMIN;

@Component
public class TokenSearcher {

    private final UserRepository userRepository;

    TokenSearcher(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean isAdmin(String token) throws RequestException {
        return userRepository.findById(Long.parseLong(getPayloadField(token, "userId"))).orElseThrow(() ->
                new RequestException(USER_IS_MISSING.getMessage(), UNAUTHORIZED)
        ).getRoleId() == ADMIN.getRole().id();

    }

    private boolean isOriginalUser(String token, long userId) throws RequestException {
        return Long.parseLong(getPayloadField(token, "userId")) == userId;
    }

    String getPayloadField(String token, String fieldName) throws RequestException {
        String payload = getPayload(token);
        try {
            return new ObjectMapper().readTree(payload).get(fieldName).asText();
        } catch (Throwable e) {
            throw new RequestException(EMPTY_TOKEN_FIELD.getMessage(), UNAUTHORIZED);
        }
    }

    public static String getPayload(String token) throws RequestException {
        String payload;
        try {
            payload = new String(
                    Base64.getDecoder().decode(
                            token.split("\\.")[1]
                    )
            );
        } catch (IllegalArgumentException e) {
            throw new RequestException(EXPIRED_OR_INVALID_TOKEN.getMessage(), INTERNAL_SERVER_ERROR);
        }
        return payload;
    }
}
