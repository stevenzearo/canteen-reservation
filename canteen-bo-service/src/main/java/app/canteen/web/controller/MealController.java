package app.canteen.web.controller;

import app.restaurant.api.MealWebService;
import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.MealView;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Map;

/**
 * @author steve
 */
public class MealController {
    @Inject
    MealWebService service;

    Response create(Request request) {
        CreateMealRequest createMealRequest = new CreateMealRequest();
        Map<String, String> paramMap = request.queryParams();
        createMealRequest.name = paramMap.get("name");
        createMealRequest.price = JSON.fromJSON(Double.class, paramMap.get("price"));
        createMealRequest.restaurantId = paramMap.get("restaurant_id");
        MealView mealView = service.create(createMealRequest);
        return Response.bean(mealView);
    }
}
