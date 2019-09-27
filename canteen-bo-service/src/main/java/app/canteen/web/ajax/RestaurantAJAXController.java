package app.canteen.web.ajax;

import app.canteen.web.ajax.restaurant.SearchRestaurantAJAXRequest;
import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.BOSearchRestaurantRequest;
import app.restaurant.api.restaurant.BOSearchRestaurantResponse;
import app.restaurant.api.restaurant.RestaurantStatusView;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class RestaurantAJAXController {
    @Inject
    BORestaurantWebService service;

    public Response search(Request request) {
        SearchRestaurantAJAXRequest controllerRequest = request.bean(SearchRestaurantAJAXRequest.class);
        BOSearchRestaurantRequest restaurantRequest = new BOSearchRestaurantRequest();
        restaurantRequest.skip = controllerRequest.skip;
        restaurantRequest.limit = controllerRequest.limit;
        restaurantRequest.name = controllerRequest.name;
        restaurantRequest.address = controllerRequest.address;
        restaurantRequest.phone = controllerRequest.phone;
        restaurantRequest.reservingDeadlineEnd = controllerRequest.reservingDeadlineEnd;
        restaurantRequest.reservingDeadlineStart = controllerRequest.reservingDeadlineStart;
        restaurantRequest.status = RestaurantStatusView.valueOf(controllerRequest.status.name());
        BOSearchRestaurantResponse searchResponse = service.search(restaurantRequest);
        return Response.bean(searchResponse);
    }
}
