package app.restaurant.api;

import app.restaurant.api.restaurant.BOCreateRestaurantRequest;
import app.restaurant.api.restaurant.BOCreateRestaurantResponse;
import app.restaurant.api.restaurant.BOGetRestaurantResponse;
import app.restaurant.api.restaurant.BOSearchRestaurantResponse;
import app.restaurant.api.restaurant.BOSearchRestaurantRequest;
import app.restaurant.api.restaurant.BOUpdateRestaurantRequest;
import core.framework.api.http.HTTPStatus;
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

    @PUT
    @Path("/bo/restaurant/:id")
    BOGetRestaurantResponse get(@PathParam("id") String id);

    @PUT
    @Path("/bo/restaurant")
    BOSearchRestaurantResponse search(BOSearchRestaurantRequest request);

    @PUT
    @Path("/bo/restaurant/:restaurantId")
    void update(@PathParam("restaurantId") String restaurantId, BOUpdateRestaurantRequest request);
}