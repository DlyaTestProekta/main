package ru.pachan.main.dto.auth.main;

public record PersonDTO(
        long id,
        String firstName,
        String surname,
        String organizationName
) {
}
