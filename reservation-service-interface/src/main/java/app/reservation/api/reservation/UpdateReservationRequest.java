package app.reservation.api.reservation;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
// todo
public class UpdateReservationRequest {
    @Property(name = "amount")
    public Double amount;

    @Property(name = "reserving_time")
    public ZonedDateTime reservingTime;

    @Property(name = "eating_time")
    public ZonedDateTime eatingTime;

    @Property(name = "status")
    public ReservationStatusView status;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "restaurant_id")
    public String restaurantId;
}
