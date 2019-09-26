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

    @Property(name = "address")
    public String address;

    @Property(name = "phone")
    public String phone;

    @NotNull
    @Property(name = "reserving_deadline_start")
    public ZonedDateTime reservingDeadlineStart = ZonedDateTime.now();

    @Property(name = "reserving_deadline_end")
    public ZonedDateTime reservingDeadlineEnd;

    @NotNull
    @Property(name = "status")
    public RestaurantStatusView status = RestaurantStatusView.OPEN;
}