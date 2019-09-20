package app.reservation.service;

import app.reservation.domain.EmailNotification;
import app.reservation.domain.EmailNotificationStatus;
import app.reservation.service.reservation.CreateEmailNotificationRequest;
import app.reservation.service.reservation.CreateEmailNotificationResponse;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.OptionalLong;

/**
 * @author steve
 */
public class EmailNotificationService {
    @Inject
    Repository<EmailNotification> repository;

    public CreateEmailNotificationResponse create(CreateEmailNotificationRequest request) {
        EmailNotification notification = new EmailNotification();
        notification.userEmail = request.userEmail;
        notification.reservationId = request.reservationId;
        notification.notifyingTime = request.notifyingTime;
        notification.sendingStatus = EmailNotificationStatus.READY;
        OptionalLong insert = repository.insert(notification);
        if (insert.isPresent()) notification.id = insert.getAsLong();
        CreateEmailNotificationResponse response = new CreateEmailNotificationResponse();
        response.id = notification.id;
        response.userEmail = notification.userEmail;
        response.reservationId = notification.reservationId;
        response.notifyingTime = notification.notifyingTime;
        return response;
    }
}
