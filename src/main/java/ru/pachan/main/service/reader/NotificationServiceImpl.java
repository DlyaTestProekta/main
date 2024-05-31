package ru.pachan.main.service.reader;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.pachan.grpc.NotificationServiceGrpc;
import ru.pachan.grpc.Reader;
import ru.pachan.main.dto.reader.NotificationDto;

import static ru.pachan.grpc.Reader.FindByPersonIdNotificationRequest;
import static ru.pachan.grpc.Reader.FindByPersonIdNotificationResponse;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @GrpcClient("reader-server")
    private NotificationServiceGrpc.NotificationServiceBlockingStub notificationServiceBlockingStub;

    public NotificationDto findByPersonIdNotification(long personId) {
        FindByPersonIdNotificationRequest findByPersonIdNotificationRequest =
                FindByPersonIdNotificationRequest.newBuilder()
                        .setPersonId(personId)
                        .build();

        try {
            FindByPersonIdNotificationResponse findByPersonIdNotificationResponse =
                    notificationServiceBlockingStub.findByPersonIdNotification(findByPersonIdNotificationRequest);
            Reader.Notification notification = findByPersonIdNotificationResponse.getNotification();
            return new NotificationDto(
                    notification.getNotificationId(),
                    notification.getPersonId(),
                    notification.getCount()
            );
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return new NotificationDto(0, 0, 0);
    }

    public NotificationDto findByIdNotification(long notificationId) {
        Reader.FindByIdNotificationRequest findByIdNotificationRequest =
                Reader.FindByIdNotificationRequest.newBuilder()
                        .setNotificationId(notificationId)
                        .build();

        try {
            Reader.FindByIdNotificationResponse findByIdNotificationResponse =
                    notificationServiceBlockingStub.findByIdNotification(findByIdNotificationRequest);
            Reader.Notification notification = findByIdNotificationResponse.getNotification();
            return new NotificationDto(
                    notification.getNotificationId(),
                    notification.getPersonId(),
                    notification.getCount()
            );
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return new NotificationDto(0, 0, 0);
    }
}
