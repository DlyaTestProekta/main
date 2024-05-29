package ru.pachan.main.dto.auth.main;

import java.util.List;

public record OrganizationDTO(
        long id,
        String name,
        List<String> persons
) {
}
