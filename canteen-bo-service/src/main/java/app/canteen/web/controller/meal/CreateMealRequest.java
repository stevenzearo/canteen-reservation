package app.canteen.web.controller.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class CreateMealRequest {
    @NotNull
    @NotBlank
    @Property(name = "restaurant_id")
    public String restaurantId;

    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "price")
    public Double price;
}
