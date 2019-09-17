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
@Collection(name = "restaurant")
public class Restaurant {
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
    @Field(name = "reserve_deadline")
    public ZonedDateTime reserveDeadline;

    @NotNull
    @Field(name = "status")
    public RestaurantStatus status;
}