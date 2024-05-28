package ru.pachan.main.util.refs.auth.user;

import java.util.List;

public record Role(
        short id,
        String name,
        List<Permission> permissions
) {
}
