package ru.pachan.main.dto.auth;

public record RefreshData(
        String refresh,
        String token,
        long roleId,
        long id
) {
}
