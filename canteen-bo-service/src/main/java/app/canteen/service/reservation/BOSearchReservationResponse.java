package app.canteen.service.reservation;

import app.reservation.api.reservation.ReservationView;
import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class BOSearchReservationResponse {
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
    public List<ReservationView> reservationList;
}
