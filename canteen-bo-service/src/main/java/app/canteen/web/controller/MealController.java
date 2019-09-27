package app.canteen.web.controller;

import app.canteen.web.controller.meal.CreateMealControllerRequest;
import app.restaurant.api.BOMealWebService;
import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.BOCreateMealResponse;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

import java.util.Map;

/**
 * @author steve
 */
public class MealController {
    @Inject
    BOMealWebService service;

    public Response create(Request request) {
        Map<String, String> paramMap = request.queryParams();
        String restaurantId = paramMap.get("restaurant_id");
        if (Strings.isBlank(restaurantId)) throw new BadRequestException("restaurant id can not be blank");
        CreateMealControllerRequest controllerRequest = request.bean(CreateMealControllerRequest.class);
        BOCreateMealRequest createMealRequest = new BOCreateMealRequest();
        createMealRequest.name = controllerRequest.name;
        createMealRequest.price = controllerRequest.price;
        BOCreateMealResponse response = service.create(restaurantId, createMealRequest);
        return Response.bean(response); // should return a page, return a bean for test.
    }
}
