package app.reservation.service.notification;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class CreateEmailNotificationRequest {
    public String userEmail;

    public String reservationId;

    public ZonedDateTime notifyingTime;

    public Restaurant restaurant;

    public List<Meal> meals;

    public static class Meal {
        public String id;

        public String name;

        public Double price;
    }

    public static class Restaurant {
        public String id;

        public String name;

        public String phone;

        public String address;
    }
}
