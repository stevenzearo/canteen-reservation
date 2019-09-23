package app.reservation.service.notification;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class CreateEmailNotificationRequest {
    public String userEmail;

    public String reservationId;

    public ZonedDateTime notifyingTime;
}
