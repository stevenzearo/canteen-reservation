package app.restaurant.api;

import app.restaurant.api.restaurant.GetRestaurantResponse;
import app.restaurant.api.restaurant.SearchRestaurantResponse;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author steve
 */
public interface RestaurantWebService {
    @GET
    @Path("/restaurant/:restaurantId")
    GetRestaurantResponse get(@PathParam("restaurantId") String restaurantId);

    @PUT
    @Path("/restaurant")
    SearchRestaurantResponse searchOpen(SearchRestaurantRequest request); // must give reserving deadline start and reserving deadline start and status
}