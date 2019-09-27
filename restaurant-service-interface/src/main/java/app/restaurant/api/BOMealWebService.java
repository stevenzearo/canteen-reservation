package app.restaurant.api;

import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.BOCreateMealResponse;
import app.restaurant.api.meal.BOGetMealResponse;
import app.restaurant.api.meal.BOSearchMealRequest;
import app.restaurant.api.meal.BOSearchMealResponse;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.BOUpdateMealRequest;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author steve
 */
public interface BOMealWebService {
    @POST
    @ResponseStatus(HTTPStatus.CREATED)
    @Path("/bo/restaurant/:restaurantId/meal")
    BOCreateMealResponse create(@PathParam("restaurantId") String restaurantId, BOCreateMealRequest request);

    @GET
    @Path("/bo/restaurant/:restaurantId/meal/:id")
    BOGetMealResponse get(@PathParam("restaurantId") String restaurantId, String mealId);

    @PUT
    @Path("/bo/restaurant/:restaurantId/meal/:id")
    void update(@PathParam("restaurantId") String restaurantId, @PathParam("id") String id, BOUpdateMealRequest request); // meal's restaurant id can't be changed.

    @PUT
    @Path("/bo/restaurant/:restaurantId/meal")
    BOSearchMealResponse search(@PathParam("restaurantId") String restaurantId, BOSearchMealRequest request); // admin can search meals via name/price range/status
}