package app.restaurant.api.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class CreateMealRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "price")
    public Double price;
}