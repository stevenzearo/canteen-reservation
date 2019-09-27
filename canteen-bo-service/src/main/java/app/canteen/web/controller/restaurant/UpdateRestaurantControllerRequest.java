package app.canteen.web.controller.restaurant;

import app.canteen.web.ajax.restaurant.RestaurantStatusAJAXView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class UpdateRestaurantControllerRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "address")
    public String address;

    @Property(name = "phone")
    public String phone;

    @Property(name = "status")
    public RestaurantStatusAJAXView status;

    @NotNull
    @Property(name = "reserving_deadline")
    public ZonedDateTime reservingDeadline;
}
