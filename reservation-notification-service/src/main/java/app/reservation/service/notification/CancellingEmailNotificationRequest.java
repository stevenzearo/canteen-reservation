package app.reservation.service.notification;

import app.reservation.domain.EmailSendingStatus;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class CancellingEmailNotificationRequest {
    public String userEmail;

    public String reservationId;
}
