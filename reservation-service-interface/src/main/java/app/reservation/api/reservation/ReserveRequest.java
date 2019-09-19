package app.reservation.api.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class ReserveRequest {
    @NotNull
    @Property(name = "amount")
    public Double amount;

    @NotNull
    @Property(name = "reserve_time")
    public ZonedDateTime reserveTime;

    @NotNull
    @Property(name = "reserve_deadline")
    public ZonedDateTime reserveDeadline;

    @NotNull
    @Property(name = "status")
    public ReservationStatus status;

    @NotNull
    @Property(name = "eat_time")
    public ZonedDateTime eatTime;

    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "restaurant_id")
    public String restaurantId;

    @NotNull
    @Property(name = "meal_id_list")
    public List<String> mealIdList;
}
