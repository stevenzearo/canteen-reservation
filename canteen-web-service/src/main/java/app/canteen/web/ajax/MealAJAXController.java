package app.canteen.web.ajax;

import app.canteen.web.ajax.meal.SearchMealAJAXRequest;
import app.restaurant.api.MealWebService;
import app.restaurant.api.meal.SearchMealRequest;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

import java.util.Map;

/**
 * @author steve
 */
public class MealAJAXController {
    @Inject
    MealWebService service;

    public Response searchValidByRestaurantId(Request request) {
        Map<String, String> queryMap = request.queryParams();
        String restaurantId = queryMap.get("restaurant_id");
        Response response = null;
        if (!Strings.isBlank(restaurantId)) {
            SearchMealAJAXRequest controllerRequest = request.bean(SearchMealAJAXRequest.class);
            SearchMealRequest searchMealRequest = new SearchMealRequest();
            searchMealRequest.skip = controllerRequest.skip;
            searchMealRequest.limit = controllerRequest.limit;
            searchMealRequest.name = controllerRequest.name;
            searchMealRequest.priceStart = controllerRequest.priceStart;
            searchMealRequest.priceEnd = controllerRequest.priceEnd;
            Response.bean(service.searchValid(restaurantId, searchMealRequest));
        } else {
            throw new BadRequestException("restaurant id can not be blank");
        }
        return response;
    }
}
