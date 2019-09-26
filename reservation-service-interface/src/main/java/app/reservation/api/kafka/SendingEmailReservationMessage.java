package app.reservation.api.kafka;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SendingEmailReservationMessage {
    @Property(name = "reservation_id")
    public String reservationId;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "reservation_deadline")
    public ZonedDateTime reservationDeadline;
}