package app.reservation.api.kafka;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class SendingEmailReservationMessage {
    @Property(name = "reservation_id")
    public String reservationId;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "restaurant_id")
    public String restaurantId;

    @Property(name = "meal_id_list")
    public List<String> mealIdList;

    @Property(name = "reservation_deadline")
    public ZonedDateTime reservationDeadline;
}