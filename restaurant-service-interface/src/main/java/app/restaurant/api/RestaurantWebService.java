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
    GetRestaurantResponse get(@PathParam("restaurantId") String restaurantId); // for get restaurant meals

    @PUT
    @Path("/restaurant")
    SearchRestaurantResponse search(SearchRestaurantRequest request); // user can only search current or future on open restaurant via name/phone/address/reserving time range
}