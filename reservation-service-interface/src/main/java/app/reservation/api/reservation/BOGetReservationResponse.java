package app.reservation.api.reservation;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class BOGetReservationResponse {
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

    @Property(name = "user_name")
    public String userName;

    @Property(name = "restaurant_id")
    public String restaurantId;

    @Property(name = "restaurant_name")
    public String restaurantName;

    @Property(name = "meals")
    public List<Meal> meals;

    public static class Meal {
        @Property(name = "id")
        public String id;

        @Property(name = "name")
        public String name;

        @Property(name = "price")
        public Double price;
    }
}
