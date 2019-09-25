package app.canteen.web.ajax;

import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Map;

/**
 * @author steve
 */
public class RestaurantBOAJAXController {
    @Inject
    BORestaurantWebService service;

    public Response searchByPage(Request request) {
        Map<String, String> paramMap = request.queryParams();
        SearchRestaurantRequest restaurantRequest = new SearchRestaurantRequest();
        restaurantRequest.skip = Integer.valueOf(paramMap.get("skip"));
        restaurantRequest.limit = Integer.valueOf(paramMap.get("limit"));
        SearchResponse response = service.search(restaurantRequest);
        return Response.bean(response);
    }
}
