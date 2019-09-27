package app.canteen.web.controller;

import app.restaurant.api.BOMealWebService;
import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.BOCreateMealResponse;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

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
        String name;
        Double price;
        String restaurantId;
        try {
            name = paramMap.get("name");
            price = Double.valueOf(paramMap.get("price"));
            restaurantId = paramMap.get("restaurant_id");
            if (Strings.isBlank(name) && Strings.isBlank(restaurantId)) throw new BadRequestException("name and restaurant id can not be blank");
        } catch (NumberFormatException e) {
            throw new BadRequestException(Strings.format("price should be a Double"));
        }
        BOCreateMealRequest.name = name;
        BOCreateMealRequest.price = price;
        BOCreateMealResponse response = service.create(restaurantId, BOCreateMealRequest);
        return Response.bean(response); // should return a page, return a bean for test.
    }
}
