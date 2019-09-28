package app.reservation.api.reservation;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class BOSearchReservationResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "reservation_list")
    public List<Reservation> reservationList;

    public static class Reservation {
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

        @Property(name = "restaurant")
        public Restaurant restaurant;

        @Property(name = "meals")
        public List<Meal> mealIdList;

        public static class Restaurant {
            @Property(name = "id")
            public String id;

            @Property(name = "name")
            public String name;

            @Property(name = "phone")
            public String phone;

            @Property(name = "address")
            public String address;
        }

        public static class Meal {
            @Property(name = "id")
            public String id;

            @Property(name = "name")
            public String name;

            @Property(name = "price")
            public String price;
        }
    }
}
