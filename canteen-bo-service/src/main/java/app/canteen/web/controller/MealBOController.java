package app.canteen.web.controller;

import app.restaurant.api.BOMealWebService;
import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.BOCreateMealResponse;
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
        BOCreateMealRequest BOCreateMealRequest = new BOCreateMealRequest();
        Map<String, String> paramMap = request.formParams();
        BOCreateMealRequest.name = paramMap.get("name");
        // todo
        BOCreateMealRequest.price = JSON.fromJSON(Double.class, paramMap.get("price"));
        BOCreateMealResponse response = service.create(paramMap.get("restaurant_id"), BOCreateMealRequest);
        return Response.bean(response); // should return a page, return a bean for test.
    }
}
