package app.restaurant.api.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class CreateMealRequest {
    @NotNull
    @Property(name = "restaurant_id")
    public String restaurantId;

    @Property(name = "name")
    public String name;

    @Property(name = "price")
    public Double price;
}