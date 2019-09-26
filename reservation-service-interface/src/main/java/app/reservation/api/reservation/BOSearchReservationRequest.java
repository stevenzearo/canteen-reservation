package app.reservation.api.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class BOSearchReservationRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "reserving_time_start")
    public ZonedDateTime reservingTimeStart;

    @Property(name = "reserving_time_end")
    public ZonedDateTime reservingTimeEnd;

    @Property(name = "status")
    public ReservationStatusView status;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "restaurant_id")
    public String restaurantId;
}
