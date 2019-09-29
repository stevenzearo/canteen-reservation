package app.reservation.api.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class ReservingRequest {
    @NotNull
    @Property(name = "amount")
    public Double amount;

    @NotNull
    @Property(name = "reserving_deadline")
    public ZonedDateTime reservingDeadline;

    @NotNull
    @Property(name = "eating_time")
    public ZonedDateTime eatingTime;

    @NotNull
    @Property(name = "user_name")
    public String userName;

    @NotNull
    @Property(name = "restaurant_id")
    public String restaurantId;

    @NotNull
    @Property(name = "restaurant_name")
    public String restaurantName;

    @NotNull
    @Property(name = "meals")
    public List<Meal> meals;

    public static class Meal {
        @NotNull
        @NotBlank
        @Property(name = "id")
        public String id;

        @NotNull
        @NotBlank
        @Property(name = "name")
        public String name;

        @NotNull
        @Property(name = "price")
        public Double price;
    }
}
