package app.reservation.service.notification;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SearchEmailNotificationRequest {
    public String userEmail;

    public String reservationId;

    public ZonedDateTime notifyingTime;
}
