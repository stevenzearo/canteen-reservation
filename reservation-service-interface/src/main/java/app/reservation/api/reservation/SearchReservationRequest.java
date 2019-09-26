package app.reservation.api.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SearchReservationRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "amount_start")
    public Double amountStart;

    @Property(name = "amount_end")
    public Double amountEnd;

    @Property(name = "reserving_time_start")
    public ZonedDateTime reservingTimeStart;

    @Property(name = "reserving_time_end")
    public ZonedDateTime reservingTimeEnd;

    @Property(name = "status")
    public ReservationStatusView status;

    @Property(name = "restaurant_id")
    public String restaurantId;
}
