package app.reservation.api.message;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class ReservationMessage {
    @Property(name = "reservation_id")
    public String reservationId;

    @Property(name = "user_id")
    public String userId;
}