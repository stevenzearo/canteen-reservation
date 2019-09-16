package app.restaurant.api.meal;

import core.framework.api.json.Property;
import org.bson.types.ObjectId;

/**
 * @author steve
 */
public class MealView {
    @Property(name = "id")
    public ObjectId id;

    @Property(name = "name")
    public String name;

    @Property(name = "price")
    public Float price;

    @Property(name = "status")
    public MealStatus status;

    @Property(name = "restaurant_id")
    public ObjectId restaurantId;
}
