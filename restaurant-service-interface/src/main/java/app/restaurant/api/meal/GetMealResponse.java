package app.restaurant.api.meal;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class GetMealResponse {
    @Property(name = "id")
    public String id;

    @Property(name = "name")
    public String name;

    @Property(name = "price")
    public Double price;
}
