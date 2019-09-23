package app.canteen.web.ajax;

import app.restaurant.api.MealWebService;
import app.restaurant.api.meal.MealStatusView;
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

    public Response searchValidByRestaurantId(Request request) {
        Map<String, String> paramMap = request.formParams();
        SearchMealRequest mealRequest = new SearchMealRequest();
        mealRequest.skip = Integer.valueOf(paramMap.get("skip"));
        mealRequest.limit = Integer.valueOf(paramMap.get("limit"));
        mealRequest.status = MealStatusView.VALID;
        return Response.bean(service.search(paramMap.get("restaurant_id"), mealRequest));
    }
}
