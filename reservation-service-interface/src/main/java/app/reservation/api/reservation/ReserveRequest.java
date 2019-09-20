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
    @Property(name = "reserving_time")
    public ZonedDateTime reservingTime;

    @NotNull
    @Property(name = "eating_time")
    public ZonedDateTime eatingTime;

    @NotNull
    @Property(name = "reserving_deadline")
    public ZonedDateTime reservingDeadline;

    @NotNull
    @Property(name = "status")
    public ReservationStatusView status;

    @NotNull
    @Property(name = "restaurant_id")
    public String restaurantId;

    @NotNull
    @Property(name = "meal_id_list")
    public List<String> mealIdList;
}
