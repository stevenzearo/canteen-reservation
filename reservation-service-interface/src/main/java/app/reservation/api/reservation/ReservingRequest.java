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
    @Property(name = "eating_time")
    public ZonedDateTime eatingTime;

    @NotNull
    @Property(name = "restaurant")
    public Restaurant restaurant;

    @NotNull
    @Property(name = "meal_list")
    public List<Meal> mealList;

    public static class Restaurant {
        @NotNull
        @NotBlank
        @Property(name = "id")
        public String id;

        @NotNull
        @NotBlank
        @Property(name = "name")
        public String name;

        @NotNull
        @NotBlank
        @Property(name = "phone")
        public String phone;

        @NotNull
        @NotBlank
        @Property(name = "address")
        public String address;

        @NotNull
        @Property(name = "reserving_deadline")
        public ZonedDateTime reservingDeadline;
    }

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
