package app.restaurant.api.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class BOCreateMealRequest {
    @NotNull
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "price")
    public Double price;
}