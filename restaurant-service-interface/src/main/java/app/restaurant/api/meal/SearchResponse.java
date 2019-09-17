package app.restaurant.api.meal;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class SearchResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "mealList")
    public List<MealView> mealViewList;
}
