package app.restaurant.api.meal;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class UpdateMealRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "price")
    public Double price;

    @Property(name = "status")
    public MealStatusView status;
}