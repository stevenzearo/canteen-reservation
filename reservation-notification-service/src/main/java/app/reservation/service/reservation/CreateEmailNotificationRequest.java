package app.reservation.service.reservation;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class CreateEmailNotificationRequest {
    public String userEmail;

    public String reservationId;

    public ZonedDateTime notifyingTime;
}
