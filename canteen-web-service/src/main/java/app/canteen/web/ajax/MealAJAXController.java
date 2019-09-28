package app.canteen.web.ajax;

import app.canteen.web.ajax.meal.SearchMealAJAXRequest;
import app.restaurant.api.MealWebService;
import app.restaurant.api.meal.SearchMealRequest;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Map;

/**
 * @author steve
 */
public class MealAJAXController {
    @Inject
    MealWebService service;

    public Response search(Request request) {
        SearchMealAJAXRequest controllerRequest = request.bean(SearchMealAJAXRequest.class);
        SearchMealRequest searchMealRequest = new SearchMealRequest();
        searchMealRequest.skip = controllerRequest.skip;
        searchMealRequest.limit = controllerRequest.limit;
        searchMealRequest.name = controllerRequest.name;
        searchMealRequest.priceStart = controllerRequest.priceStart;
        searchMealRequest.priceEnd = controllerRequest.priceEnd;
        return Response.bean(service.searchValid(controllerRequest.restaurantId, searchMealRequest));

    }
}
