package app.reservation.api.reservation;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class SearchReservationResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "reservation_list")
    public List<ReservationView> reservationViewList;
}
