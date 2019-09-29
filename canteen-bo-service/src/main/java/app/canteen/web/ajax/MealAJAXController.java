package app.canteen.web.ajax;

import app.canteen.web.ajax.meal.CreateMealAJAXRequest;
import app.restaurant.api.BOMealWebService;
import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.BOCreateMealResponse;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class MealAJAXController {
    @Inject
    BOMealWebService service;

    public Response create(Request request) {
        String restaurantId = request.pathParam("id");
        CreateMealAJAXRequest controllerRequest = request.bean(CreateMealAJAXRequest.class);
        BOCreateMealRequest createMealRequest = new BOCreateMealRequest();
        createMealRequest.name = controllerRequest.name;
        createMealRequest.price = controllerRequest.price;
        BOCreateMealResponse response = service.create(restaurantId, createMealRequest);
        return Response.bean(response);
    }
}
