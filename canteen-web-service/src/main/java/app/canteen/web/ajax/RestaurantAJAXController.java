package app.canteen.web.ajax;

import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.RestaurantStatusView;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author steve
 */
public class RestaurantAJAXController {
    @Inject
    RestaurantWebService service;

    public Response searchAvailableByDate(Request request) {
        Map<String, String> paramMap = request.formParams();
        SearchRestaurantRequest restaurantRequest = new SearchRestaurantRequest();
        restaurantRequest.skip = Integer.valueOf(paramMap.get("skip"));
        restaurantRequest.limit = Integer.valueOf(paramMap.get("limit"));
        restaurantRequest.reservingDeadlineStart = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_deadline_lt"));
        restaurantRequest.status = RestaurantStatusView.OPEN;
        return Response.bean(service.search(restaurantRequest));
    }
}
