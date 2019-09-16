package app.restaurant.api.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;
import org.bson.types.ObjectId;

/**
 * @author steve
 */
public class SearchRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "name")
    public String name;

    @Property(name = "price")
    public Float price;

    @Property(name = "status")
    public MealStatus status;

    @Property(name = "restaurant_id")
    public ObjectId restaurantId;
}