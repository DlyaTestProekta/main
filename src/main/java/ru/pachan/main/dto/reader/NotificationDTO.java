package ru.pachan.main.dto.reader;

public record NotificationDTO(
        long notification_id,
        long person_id,
        long count
) {
}
