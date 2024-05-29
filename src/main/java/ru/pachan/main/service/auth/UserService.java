package ru.pachan.main.service.auth;

import org.springframework.data.domain.Pageable;
import ru.pachan.main.dto.auth.Authorization;
import ru.pachan.main.dto.auth.RefreshData;
import ru.pachan.main.dto.auth.dictionary.PaginatedResponse;
import ru.pachan.main.exception.data.RequestException;
import ru.pachan.main.model.auth.User;

public interface UserService {
    PaginatedResponse<User> getAll(Pageable pageable);

    User createOne(User user) throws RequestException;

    User getOne(long id, String token) throws RequestException;

    User updateOne(long id, String token, User user) throws RequestException;

    void deleteOne(long id) throws RequestException;
}
