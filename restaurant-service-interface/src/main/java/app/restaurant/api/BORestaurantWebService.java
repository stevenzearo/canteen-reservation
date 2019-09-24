package app.restaurant.api;

import app.restaurant.api.restaurant.CreateRestaurantRequest;
import app.restaurant.api.restaurant.CreateRestaurantResponse;
import app.restaurant.api.restaurant.GetRestaurantResponse;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.api.restaurant.UpdateRestaurantRequest;
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
    CreateRestaurantResponse create(CreateRestaurantRequest createRestaurantRequest);

    @GET
    @Path("/bo/restaurant/:restaurantId")
    GetRestaurantResponse get(@PathParam("restaurantId") String restaurantId);

    @PUT
    @Path("/bo/restaurant")
    SearchResponse search(SearchRestaurantRequest request);

    @PUT
    @Path("/bo/restaurant/:restaurantId")
    void update(@PathParam("restaurantId") String restaurantId, UpdateRestaurantRequest request);
}