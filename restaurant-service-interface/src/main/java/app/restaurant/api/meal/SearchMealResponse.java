package app.restaurant.api.meal;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class SearchMealResponse {
    @Property(name = "total")
    public Long total;

    // todo
    @Property(name = "meals")
    public List<MealView> meals;
}