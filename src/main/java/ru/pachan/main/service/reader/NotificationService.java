package ru.pachan.main.service.reader;

import ru.pachan.main.dto.reader.NotificationDTO;

public interface NotificationService {
    NotificationDTO findByPersonIdNotification(long personId);

    NotificationDTO findByIdNotification(long notificationId);
}
