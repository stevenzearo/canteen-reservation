package app.canteen.web.ajax;

import app.canteen.web.ajax.restaurant.SearchRestaurantAJAXRequest;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

import java.util.Map;

/**
 * @author steve
 */
public class RestaurantAJAXController {
    @Inject
    RestaurantWebService service;

    public Response get(Request request) {
        Response response;
        Map<String, String> paramMap = request.queryParams();
        String restaurantId = paramMap.get("restaurant_id");
        if (!Strings.isBlank(restaurantId)) {
            response = Response.bean(service.get(restaurantId));
        } else {
            throw new BadRequestException("restaurant id can not be blank or null");
        }
        return response;
    }

    public Response search(Request request) {
        SearchRestaurantAJAXRequest controllerRequest = request.bean(SearchRestaurantAJAXRequest.class);
        SearchRestaurantRequest restaurantRequest = new SearchRestaurantRequest();
        restaurantRequest.skip = controllerRequest.skip;
        restaurantRequest.limit = controllerRequest.limit;
        restaurantRequest.name = controllerRequest.name;
        restaurantRequest.reservingDeadlineStart = controllerRequest.reservingDeadlineStart;
        restaurantRequest.reservingDeadlineEnd = controllerRequest.reservingDeadlineEnd;
        restaurantRequest.address = controllerRequest.address;
        restaurantRequest.phone = controllerRequest.phone;
        return Response.bean(service.searchOpen(restaurantRequest));
    }
}
