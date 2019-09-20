package app.restaurant.api;

import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author steve
 */
public interface MealWebService {
    @PUT
    @Path("/restaurant/:restaurantId/meal")
    SearchMealResponse search(@PathParam("restaurantId") String restaurantId, SearchMealRequest request);
}