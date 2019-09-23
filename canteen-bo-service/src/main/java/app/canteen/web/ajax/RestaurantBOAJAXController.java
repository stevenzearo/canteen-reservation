package app.canteen.web.ajax;

import app.restaurant.api.RestaurantWebService;
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
    RestaurantWebService service;

    public Response searchByPage(Request request) {
        Map<String, String> paramMap = request.queryParams();
        Integer pageNumber = Integer.valueOf(paramMap.get("page_number"));
        Integer pageSize = Integer.valueOf(paramMap.get("page_size"));
        Integer skip = 0;
        Integer limit = 10;
        if (pageNumber > 0 && pageSize > 0) {
            limit = pageSize;
            skip = (pageNumber - 1) * pageSize;
        }
        SearchRestaurantRequest restaurantRequest = new SearchRestaurantRequest();
        restaurantRequest.skip = skip;
        restaurantRequest.limit = limit;
        SearchResponse response = service.search(restaurantRequest);
        return Response.bean(response);
    }
}
