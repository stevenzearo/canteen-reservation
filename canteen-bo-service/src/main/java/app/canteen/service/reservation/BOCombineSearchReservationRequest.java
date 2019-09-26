package app.canteen.service.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class BOCombineSearchReservationRequest {
    @NotNull
    @Property(name = "user_skip")
    public Integer userSkip = 0;

    @NotNull
    @Property(name = "user_limit")
    public Integer userLimit = 10;

    @NotNull
    @Property(name = "restaurant_skip")
    public Integer restaurantSkip = 0;

    @NotNull
    @Property(name = "restaurant_limit")
    public Integer restaurantLimit = 10;

    @NotNull
    @Property(name = "restaurant_skip")
    public Integer reservationSkip = 0;

    @NotNull
    @Property(name = "restaurant_limit")
    public Integer reservationLimit = 10;

    @Property(name = "userName")
    public String userName;

    @Property(name = "restaurantName")
    public String restaurantName;

    @Property(name = "reserving_date")
    public ZonedDateTime reservingDate;
}
