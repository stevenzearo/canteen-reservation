package app.canteen.web.ajax.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SearchReservationAJAXRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "restaurant_name")
    public String restaurantName;

    @Property(name = "reserving_time_start")
    public ZonedDateTime reservingTimeStart;

    @Property(name = "reserving_time_end")
    public ZonedDateTime reservingTimeEnd;
}
