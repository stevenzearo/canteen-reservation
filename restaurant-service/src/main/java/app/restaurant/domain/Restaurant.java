package app.restaurant.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
@Collection(name = "restaurants")
public class Restaurant {
    // todo
    @NotNull
    @Id
    @Field(name = "id")
    public String id;

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
    @Field(name = "reserving_deadline")
    public ZonedDateTime reservingDeadline;

    @NotNull
    @Field(name = "status")
    public RestaurantStatus status;
}