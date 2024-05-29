package ru.pachan.main.service.auth;

import ru.pachan.main.dto.auth.Authorization;
import ru.pachan.main.dto.auth.RefreshData;
import ru.pachan.main.exception.data.RequestException;

public interface AuthorizationService {
    RefreshData generate(Authorization authorization) throws RequestException;

    RefreshData refresh(String token) throws RequestException;
}
