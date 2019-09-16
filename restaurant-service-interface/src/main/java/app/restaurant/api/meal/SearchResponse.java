package app.restaurant.api.meal;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class SearchResponse {
    @Property(name = "total")
    Integer total;

    @Property(name = "mealList")
    List<MealView> mealViewList;
}
