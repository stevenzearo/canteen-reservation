package app.restaurant.api;

import app.restaurant.api.restaurant.CreateRestaurantRequest;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.api.restaurant.UpdateRestaurantRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author steve
 */
public interface RestaurantWebService {
    @POST
    @Path("/restaurant")
    RestaurantView create(CreateRestaurantRequest createRestaurantRequest);

    @GET
    @Path("/restaurant/:id")
    RestaurantView get(@PathParam("id") String id);

    @PUT
    @Path("/restaurant")
    SearchResponse searchListByConditions(SearchRestaurantRequest request);

    @POST
    @Path("/restaurant/:id")
    void update(@PathParam("id") String id, UpdateRestaurantRequest request);
}