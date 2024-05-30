package ru.pachan.main.dto.main;

public record PersonDTO(
        long id,
        String firstName,
        String surname,
        String organizationName
) {
}
