package ru.pachan.main.service.reader;

import ru.pachan.main.dto.reader.NotificationDto;

public interface NotificationService {
    NotificationDto findByPersonIdNotification(long personId);

    NotificationDto findByIdNotification(long notificationId);
}
