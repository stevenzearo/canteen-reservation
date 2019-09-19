package app.restaurant.api.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class SearchMealRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "name")
    public String name;

    @Property(name = "price_eq")
    public Double priceEq;

    @Property(name = "price_lte")
    public Double priceLte;

    @Property(name = "price_gte")
    public Double priceGte;

    @Property(name = "status")
    public MealStatus status;

    @Property(name = "restaurant_id")
    public String restaurantId;
}