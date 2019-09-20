package app.reservation.api.message;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SendEmailReservationMessage {
    @Property(name = "reservation_id")
    public String reservationId;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "reservation_deadline")
    public ZonedDateTime reservationDeadline;
}