package ru.pachan.main.util.refs.auth.user;

public record Permission(
        short permissionLevel,
        String uname,
        String summary
) {
}

