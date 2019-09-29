package app.canteen.web.ajax.restaurant;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class UpdateAJAXRestaurantRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "address")
    public String address;

    @Property(name = "phone")
    public String phone;

    @NotNull
    @Property(name = "status")
    public RestaurantStatusAJAXView status;

    @NotNull
    @Property(name = "reserving_deadline")
    public ZonedDateTime reservingDeadline;
}
