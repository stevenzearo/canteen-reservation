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

    // todo
    @Property(name = "price_equal")
    public Double priceEqual;

    @Property(name = "price_less_than_equal")
    public Double priceLessThanEqual;

    @Property(name = "price_greater_than_equal")
    public Double priceGreaterThanEqual;

    @Property(name = "status")
    public MealStatusView status;
}