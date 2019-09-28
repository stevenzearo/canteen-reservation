package app.restaurant.api;

import app.restaurant.api.meal.BOGetMealResponse;
import app.restaurant.api.meal.GetMealResponse;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author steve
 */
public interface MealWebService {
    @GET
    @Path("/restaurant/:restaurantId/meal/:mealId")
    GetMealResponse get(@PathParam("restaurantId") String restaurantId, @PathParam("mealId") String mealId);
    @PUT
    @Path("/restaurant/:restaurantId/meal")
    SearchMealResponse searchValid(@PathParam("restaurantId") String restaurantId, SearchMealRequest request); // user can search via name/price range
}