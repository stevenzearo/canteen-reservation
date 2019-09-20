package app.restaurant.api;

import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.CreateMealResponse;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.UpdateMealRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author steve
 */
public interface BOMealWebService {
    @POST
    @Path("/bo/restaurant/:restaurantId/meal")
    CreateMealResponse create(@PathParam("restaurantId") String restaurantId, CreateMealRequest request);

    @PUT
    @Path("/bo/restaurant/:restaurantId/meal/:id")
    void update(@PathParam("restaurantId") String restaurantId, @PathParam("id") String id, UpdateMealRequest request); // meal's restaurant id can't be changed.

    @PUT
    @Path("/bo/restaurant/:restaurantId/meal")
    SearchMealResponse search(@PathParam("restaurantId") String restaurantId, SearchMealRequest request);
}