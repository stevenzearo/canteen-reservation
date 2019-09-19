package app.restaurant.api.restaurant;

import app.restaurant.api.meal.MealView;
import core.framework.api.json.Property;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class RestaurantView {
    @Property(name = "id")
    public String id;

    @Property(name = "name")
    public String name;

    @Property(name = "address")
    public String address;

    @Property(name = "phone")
    public String phone;

    @Property(name = "status")
    public RestaurantStatus status;

    @Property(name = "reserving_deadline")
    public ZonedDateTime reserveDeadline;
}