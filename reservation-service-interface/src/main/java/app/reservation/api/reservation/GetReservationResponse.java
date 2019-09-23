package app.reservation.api.reservation;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class GetReservationResponse {
    @Property(name = "id")
    public String id;

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

    @Property(name = "meal_id_list")
    public List<String> mealIdList;
}
