package app.canteen.web.ajax;

import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.RestaurantStatus;
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

    public Response searchAvailableListByDate(Request request) {
        Map<String, String> paramMap = request.queryParams();
        SearchRestaurantRequest restaurantRequest = new SearchRestaurantRequest();
        restaurantRequest.skip = Integer.valueOf(paramMap.get("skip"));
        restaurantRequest.limit = Integer.valueOf(paramMap.get("limit"));
        restaurantRequest.reserveDeadline = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_deadline"));
        restaurantRequest.status = RestaurantStatus.OPEN;
        return Response.bean(service.searchListByConditions(restaurantRequest));
    }
}
