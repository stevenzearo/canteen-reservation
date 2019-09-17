package app.restaurant.api;

import app.restaurant.api.meal.CreateRequest;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchRequest;
import app.restaurant.api.meal.SearchResponse;
import app.restaurant.api.meal.UpdateRequest;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author steve
 */
public interface MealWebService {
    @POST
    @Path("/restaurant/meal")
    MealView create(CreateRequest request);

    @POST
    @Path("/restaurant/meal/:id")
    void update(@PathParam("id") String id, UpdateRequest request);

    @PUT
    @Path("/restaurant/meal")
    SearchResponse searchListByConditions(SearchRequest request);
}
