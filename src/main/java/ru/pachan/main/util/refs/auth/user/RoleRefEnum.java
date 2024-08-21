package ru.pachan.main.util.refs.auth.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static ru.pachan.main.util.enums.UnameEnum.*;
import static ru.pachan.main.util.refs.auth.user.PermissionLevelEnum.DELETE;
import static ru.pachan.main.util.refs.auth.user.PermissionLevelEnum.READ;

@Getter
@AllArgsConstructor
public enum RoleRefEnum {
    ADMIN(new Role((short) 1, "Администратор", Arrays.asList(
            new Permission(DELETE.getId(), USER.getUname(), USER.getDescription()),
            new Permission(DELETE.getId(), PERSON.getUname(), PERSON.getDescription()),
            new Permission(DELETE.getId(), ORGANIZATION.getUname(), ORGANIZATION.getDescription()),
            new Permission(DELETE.getId(), CERTIFICATE.getUname(), CERTIFICATE.getDescription()),
            new Permission(DELETE.getId(), SKILL.getUname(), SKILL.getDescription()),
            new Permission(DELETE.getId(), NOTIFICATION.getUname(), NOTIFICATION.getDescription())
    ))),
    WORKER(new Role((short) 2, "Работник", Arrays.asList(
            new Permission(DELETE.getId(), ORGANIZATION.getUname(), ORGANIZATION.getDescription()),
            new Permission(READ.getId(), CERTIFICATE.getUname(), CERTIFICATE.getDescription())
    )));

    private final Role role;

    public static HashMap<String, Short> getPermissionsByRoleId(short roleId) {
        return Arrays.stream(values())
                .filter(entry -> entry.getRole().id() == roleId)
                .findFirst()
                .map(entry -> entry.getRole().permissions().stream()
                        .collect(Collectors.toMap(Permission::uname, Permission::permissionLevel,
                                (existing, replacement) -> existing,
                                HashMap::new))
                )
                .orElse(new HashMap<>());
    }

    public static List<Role> getAll() {
        return Arrays.stream(values())
                .map(RoleRefEnum::getRole)
                .collect(Collectors.toList());
    }
}
