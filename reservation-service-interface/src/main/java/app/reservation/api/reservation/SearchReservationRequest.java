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

    @Property(name = "amount_eq")
    public Double amountEqual;

    @Property(name = "amount_elt")
    public Double amountEqualLimitThan;

    @Property(name = "amount_egt")
    public Double amountEqualGreaterThan;

    @Property(name = "reserve_time_elt")
    public ZonedDateTime reserveTimeEqualLaterThan;

    @Property(name = "reserve_time_ebt")
    public ZonedDateTime reserveTimeEqualBeforeThan;

    @Property(name = "reserve_time_eq")
    public ZonedDateTime reserveTimeEqual;

    @Property(name = "status")
    public ReservationStatus status;

    @Property(name = "eat_time_elt")
    public ZonedDateTime eatTimeEqualLaterThan;

    @Property(name = "eat_time_ebt")
    public ZonedDateTime eatTimeEqualBeforeThan;

    @Property(name = "eat_time_eq")
    public ZonedDateTime eatTimeEqual;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "restaurant_id")
    public String restaurantId;
}
