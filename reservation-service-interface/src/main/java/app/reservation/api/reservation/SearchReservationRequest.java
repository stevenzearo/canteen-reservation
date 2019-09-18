package app.reservation.api.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SearchReservationRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "amount")
    public Double amount;

    @Property(name = "reserve_time")
    public ZonedDateTime reserveTime;

    @Property(name = "status")
    public ReservationStatus status;

    @Property(name = "eat_time")
    public ZonedDateTime eatTime;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "restaurant_id")
    public String restaurantId;
}
