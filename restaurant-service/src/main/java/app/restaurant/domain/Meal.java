package app.restaurant.domain;

import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;
import org.bson.types.ObjectId;

/**
 * @author steve
 */

@Collection(name = "meal")
public class Meal {
    @NotNull
    @Id
    @Field(name = "id")
    public ObjectId id;

    @Field(name = "name")
    public String name;

    @NotNull
    @Field(name = "price")
    public Float price;

    @NotNull
    @Field(name = "status")
    public MealStatus status;
}
