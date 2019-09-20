package app.restaurant.api.restaurant;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class CreateRestaurantRequest {
    @NotNull
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "address")
    public String address;

    @NotNull
    @Property(name = "phone")
    public String phone;

    @Property(name = "reserving_deadline")
    public ZonedDateTime reservingDeadline;
}