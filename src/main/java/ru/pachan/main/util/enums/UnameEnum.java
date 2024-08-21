package ru.pachan.main.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnameEnum {
    USER("user", "Пользователь"),
    PERSON("person", "Сотрудник"),
    ORGANIZATION("organization", "Организация"),
    CERTIFICATE("certificate", "Удостоверение"),
    SKILL("skill", "Умение"),
    NOTIFICATION("notification", "Уведомление");

    private final String uname;
    private final String description;
}
