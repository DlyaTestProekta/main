package ru.pachan.main.service.auth;

import ru.pachan.main.dto.auth.AuthorizationDto;
import ru.pachan.main.dto.auth.RefreshDataDto;
import ru.pachan.main.exception.data.RequestException;

public interface AuthorizationService {
    RefreshDataDto generate(AuthorizationDto authorizationDto) throws RequestException;

    RefreshDataDto refresh(String token) throws RequestException;
}
