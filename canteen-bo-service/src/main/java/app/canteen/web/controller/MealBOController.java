package app.canteen.web.controller;

import app.restaurant.api.BOMealWebService;
import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.CreateMealResponse;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Map;

/**
 * @author steve
 */
public class MealBOController {
    @Inject
    BOMealWebService service;

    public Response create(Request request) {
        CreateMealRequest createMealRequest = new CreateMealRequest();
        Map<String, String> paramMap = request.formParams();
        createMealRequest.name = paramMap.get("name");
        createMealRequest.price = JSON.fromJSON(Double.class, paramMap.get("price"));
        CreateMealResponse response = service.create(paramMap.get("restaurant_id"), createMealRequest);
        return Response.bean(response); // should return a module, return a bean for test.
    }
}
