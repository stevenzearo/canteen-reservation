package app.restaurant.api.restaurant;

import core.framework.api.json.Property;
import core.framework.api.web.service.ResponseStatus;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class UpdateRestaurantRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "address")
    public String address;

    @Property(name = "phone")
    public String phone;

    @Property(name = "status")
    public RestaurantStatus status;

    @Property(name = "reserve_deadline")
    public ZonedDateTime reserveDeadline;
}