package app.reservation.api.kafka;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class CancellingReservationMessage {
    @Property(name = "reservation_id")
    public String reservationId;

    @Property(name = "user_id")
    public Long userId;
}