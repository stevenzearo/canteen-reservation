package app.restaurant.domain;

import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;

/**
 * @author steve
 */

@Collection(name = "meals")
public class Meal {
    @NotNull
    @Id
    @Field(name = "id")
    public String id;

    @Field(name = "name")
    public String name;

    @NotNull
    @Field(name = "price")
    public Double price;

    @NotNull
    @Field(name = "status")
    public MealStatus status;

    @NotNull
    @Field(name = "restaurant_id")
    public String restaurantId;
}