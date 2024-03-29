package app.restaurant.api.restaurant;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class BOSearchRestaurantRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "name")
    public String name;

    @Property(name = "phone")
    public String phone;

    @Property(name = "address")
    public String address;

    @Property(name = "reserving_deadline_start")
    public ZonedDateTime reservingDeadlineStart;

    @Property(name = "reserving_deadline_end")
    public ZonedDateTime reservingDeadlineEnd;

    @Property(name = "status")
    public RestaurantStatusView status;
}