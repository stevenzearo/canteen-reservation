package app.restaurant.api.restaurant;

import core.framework.api.json.Property;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class RestaurantView {
    @Property(name = "id")
    public String id;

    @Property(name = "name")
    public String name;

    @Property(name = "address")
    public String address;

    @Property(name = "phone")
    public String phone;

    @Property(name = "status")
    public RestaurantStatusView status;

    @Property(name = "reserving_deadline")
    public ZonedDateTime reservingDeadline;
}