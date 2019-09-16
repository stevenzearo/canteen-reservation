package app.restaurant.api.restaurant;

import core.framework.api.json.Property;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class RestaurantView {
    @Property(name = "id")
    public ObjectId id;

    @Property(name = "name")
    public String name;

    @Property(name = "address")
    public String address;

    @Property(name = "phone")
    public String phone;

    @Property(name = "Status")
    public RestaurantStatus status;

    @Property(name = "reserve_deadline")
    public ZonedDateTime reserveDeadline;

    @Property(name = "meal_list")
    public List<MealView> mealViewList;
}