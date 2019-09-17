package app.restaurant.api.restaurant;

import app.restaurant.api.meal.MealStatus;
import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SearchRestaurantRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "name")
    public String name;

    @Property(name = "address")
    public String address;

    @Property(name = "phone")
    public String phone;

    @Property(name = "reserve_deadline")
    public ZonedDateTime reserveDeadline;

    @Property(name = "status")
    public MealStatus status;
}