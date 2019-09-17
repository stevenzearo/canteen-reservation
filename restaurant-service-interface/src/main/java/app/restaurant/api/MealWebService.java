package app.restaurant.api;

import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.UpdateMealRequest;
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
    MealView create(CreateMealRequest request);

    @POST
    @Path("/restaurant/meal/:id")
    void update(@PathParam("id") String id, UpdateMealRequest request);

    @PUT
    @Path("/restaurant/meal")
    SearchMealResponse searchListByConditions(SearchMealRequest request);
}