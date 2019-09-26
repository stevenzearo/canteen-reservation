package app.restaurant.api.meal;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class SearchMealResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "meals")
    public List<MealView> meals;

    public static class MealView {
        @Property(name = "id")
        public String id;

        @Property(name = "name")
        public String name;

        @Property(name = "price")
        public Double price;

        @Property(name = "status")
        public MealStatusView status;
    }
}