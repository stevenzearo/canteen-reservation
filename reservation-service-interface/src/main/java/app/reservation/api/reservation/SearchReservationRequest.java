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

    @Property(name = "amount_equal")
    public Double amountEqual;

    @Property(name = "amount_equal_limit_than")
    public Double amountEqualLimitThan;

    @Property(name = "amount_equal_greater_than")
    public Double amountEqualGreaterThan;

    @Property(name = "reserving_time_equal_later_than")
    public ZonedDateTime reservingTimeEqualLaterThan;

    @Property(name = "reserving_time_equal_before_than")
    public ZonedDateTime reservingTimeEqualBeforeThan;

    @Property(name = "reserving_time_equal")
    public ZonedDateTime reservingTimeEqual;

    @Property(name = "status")
    public ReservationStatusView status;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "restaurant_id")
    public String restaurantId;
}
