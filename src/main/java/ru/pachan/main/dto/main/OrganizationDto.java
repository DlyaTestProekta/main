package ru.pachan.main.dto.main;

import java.util.List;

public record OrganizationDto(
        long id,
        String name,
        List<String> persons
) {
}
