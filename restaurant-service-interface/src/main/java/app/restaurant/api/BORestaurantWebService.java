package app.restaurant.api;

import app.restaurant.api.restaurant.BOCreateRestaurantRequest;
import app.restaurant.api.restaurant.BOCreateRestaurantResponse;
import app.restaurant.api.restaurant.BOGetRestaurantResponse;
import app.restaurant.api.restaurant.BOSearchRestaurantResponse;
import app.restaurant.api.restaurant.BOSearchRestaurantRequest;
import app.restaurant.api.restaurant.BOUpdateRestaurantRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author steve
 */
public interface BORestaurantWebService {
    @POST
    @ResponseStatus(HTTPStatus.CREATED)
    @Path("/bo/restaurant")
    BOCreateRestaurantResponse create(BOCreateRestaurantRequest request);

    @GET
    @Path("/bo/restaurant/:restaurantId")
    BOGetRestaurantResponse get(@PathParam("restaurantId") String id);

    @PUT
    @Path("/bo/restaurant")
    BOSearchRestaurantResponse search(BOSearchRestaurantRequest request); // admin can search meals via name/phone/address/reserving deadline range/status

    @PUT
    @Path("/bo/restaurant/:restaurantId")
    void update(@PathParam("restaurantId") String restaurantId, BOUpdateRestaurantRequest request); // admin can partially update name, phone, address, status, reserving deadline, but must give reserving deadline
}