package app.reservation.api.reservation;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class ReservationView {
    @Property(name = "id")
    public String id;

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

    @Property(name = "meal_id_list")
    public List<String> mealIdList;
}
