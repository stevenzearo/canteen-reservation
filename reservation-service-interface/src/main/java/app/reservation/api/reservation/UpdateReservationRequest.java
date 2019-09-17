package app.reservation.api.reservation;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class UpdateReservationRequest {
    @Property(name = "amount")
    public Double amount;

    @Property(name = "reserve_time")
    public ZonedDateTime reserveTime;

    @Property(name = "status")
    public ReservationStatus status;

    @Property(name = "eat_time")
    public ZonedDateTime eatTime;

    @Property(name = "user_id")
    public String userId;

    @Property(name = "restaurant_id")
    public String restaurantId;
}
