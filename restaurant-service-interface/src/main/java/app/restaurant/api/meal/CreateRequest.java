package app.restaurant.api.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;
import org.bson.types.ObjectId;

/**
 * @author steve
 */
public class CreateRequest {
    @NotNull
    @Property(name = "restaurant_id")
    public ObjectId restaurantId;

    @Property(name = "name")
    public String name;

    @Property(name = "price")
    public Float price;
}
