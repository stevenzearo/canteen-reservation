package app.restaurant.api;

import app.restaurant.api.meal.SearchRequest;
import app.restaurant.api.meal.SearchResponse;
import app.restaurant.api.meal.UpdateRequest;
import app.restaurant.api.restaurant.CreateRequest;
import app.restaurant.api.restaurant.CreateResponse;
import app.restaurant.api.restaurant.RestaurantView;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import org.bson.types.ObjectId;

/**
 * @author steve
 */
public interface RestaurantWebService {
    @POST
    @Path("/restaurant")
    CreateResponse create(CreateRequest createRequest);

    @GET
    @Path("/restaurant/:id")
    RestaurantView get(@PathParam("id") ObjectId id);

    @PUT
    @Path("/restaurant")
    SearchResponse searchByConditions(SearchRequest request);

    @POST
    @Path("/restaurant/:id")
    void update(UpdateRequest request);
}
