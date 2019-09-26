package app.restaurant.api.meal;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class BOSearchMealResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "meals")
    public List<Meal> meals;

    public static class Meal {
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