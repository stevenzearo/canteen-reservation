package app.restaurant.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
@Collection(name = "restaurant")
public class Restaurant {
    @NotNull
    @Id
    @Field(name = "id")
    public ObjectId id;

    @NotNull
    @NotBlank
    @Field(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Field(name = "address")
    public String address;

    @NotNull
    @NotBlank
    @Field(name = "phone")
    public String phone;

    @NotNull
    @Field(name = "reserve_deadline")
    public ZonedDateTime reserveDeadline;

    @NotNull
    @Field(name = "status")
    public RestaurantStatus status;

    @Field(name = "mealList")
    public List<Meal> mealList;
}
