package app.restaurant.api;

import app.restaurant.api.restaurant.CreateRequest;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchRequest;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.api.restaurant.UpdateRequest;
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
    RestaurantView create(CreateRequest createRequest);

    @GET
    @Path("/restaurant/:id")
    RestaurantView get(@PathParam("id") String id);

    @PUT
    @Path("/restaurant")
    SearchResponse searchListByConditions(SearchRequest request);

    @POST
    @Path("/restaurant/:id")
    void update(@PathParam("id") String id, UpdateRequest request);
}
