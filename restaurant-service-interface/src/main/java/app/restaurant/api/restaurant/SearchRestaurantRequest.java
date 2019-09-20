package app.restaurant.api.restaurant;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SearchRestaurantRequest {
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

    @Property(name = "reserving_deadline_later_than")
    public ZonedDateTime reservingDeadlineLaterThan;

    @Property(name = "reserving_deadline_equal")
    public ZonedDateTime reservingDeadlineEqual;

    @Property(name = "reserving_deadline_before_than")
    public ZonedDateTime reservingDeadlineBeforeThan;

    @Property(name = "status")
    public RestaurantStatusView status;
}