package app.canteen.service.reservation;

import app.reservation.api.reservation.ReservationStatusView;
import core.framework.api.json.Property;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class CombineSearchReservationResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "user_total")
    public Long userTotal;

    @Property(name = "restaurant_total")
    public Long restaurantTotal;

    @Property(name = "user_id_list")
    public List<Long> userIdList;

    @Property(name = "restaurant_id_list")
    public List<String> restaurantIdList;

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

        @Property(name = "restaurant_id")
        public String restaurantId;

        @Property(name = "meal_id_list")
        public List<String> mealIdList;
    }

}
