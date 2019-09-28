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

    @Property(name = "user")
    public User user;

    @Property(name = "restaurant")
    public Restaurant restaurant;

    @Property(name = "meals")
    public List<Meal> meals;

    public static class User {
        @Property(name = "id")
        public Long id;

        @Property(name = "name")
        public String name;

        @Property(name = "email")
        public String email;
    }

    public static class Meal {
        @Property(name = "id")
        public String id;

        @Property(name = "name")
        public String name;

        @Property(name = "price")
        public Double price;
    }

    public static class Restaurant {
        @Property(name = "id")
        public String id;

        @Property(name = "name")
        public String name;

        @Property(name = "phone")
        public Double phone;

        @Property(name = "address")
        public Double address;
    }
}
