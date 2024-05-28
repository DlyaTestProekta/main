package ru.pachan.main.util.refs.auth.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionLevelEnum {
    NO_ACCESS((short) 0),
    READ((short) 1),
    WRITE((short) 2),
    UPDATE((short) 3),
    DELETE((short) 4);

    private final short id;

}
