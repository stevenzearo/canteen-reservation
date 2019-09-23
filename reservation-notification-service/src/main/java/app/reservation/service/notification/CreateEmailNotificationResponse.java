package app.reservation.service.notification;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class CreateEmailNotificationResponse {
    public Long id;

    public String userEmail;

    public String reservationId;

    public ZonedDateTime notifyingTime;

    public EmailSendingStatusView sendingStatus;
}
