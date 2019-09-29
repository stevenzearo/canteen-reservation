package app.canteen.web.ajax;

import app.canteen.web.ajax.restaurant.CreateRestaurantAJAXRequest;
import app.canteen.web.ajax.restaurant.SearchRestaurantAJAXRequest;
import app.canteen.web.ajax.restaurant.UpdateAJAXRestaurantRequest;
import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.BOCreateRestaurantRequest;
import app.restaurant.api.restaurant.BOSearchRestaurantRequest;
import app.restaurant.api.restaurant.BOSearchRestaurantResponse;
import app.restaurant.api.restaurant.BOUpdateRestaurantRequest;
import app.restaurant.api.restaurant.RestaurantStatusView;
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
    BORestaurantWebService service;

    public Response create(Request request) {
        CreateRestaurantAJAXRequest controllerRequest = request.bean(CreateRestaurantAJAXRequest.class);
        BOCreateRestaurantRequest createRequest = new BOCreateRestaurantRequest();
        createRequest.name = controllerRequest.name;
        createRequest.phone = controllerRequest.phone;
        createRequest.address = controllerRequest.address;
        createRequest.reservingDeadline = controllerRequest.reservingDeadline;
        return Response.bean(service.create(createRequest)); // should return a page, return text for test.
    }

    public Response update(Request request) {
        UpdateAJAXRestaurantRequest controllerRequest = request.bean(UpdateAJAXRestaurantRequest.class);
        BOUpdateRestaurantRequest updateRestaurantRequest = new BOUpdateRestaurantRequest();
        updateRestaurantRequest.name = controllerRequest.name;
        updateRestaurantRequest.phone = controllerRequest.phone;
        updateRestaurantRequest.address = controllerRequest.address;
        updateRestaurantRequest.status = RestaurantStatusView.valueOf(controllerRequest.status.name());
        updateRestaurantRequest.reservingDeadline = controllerRequest.reservingDeadline;
        String id = request.pathParam("id");
        service.update(id, updateRestaurantRequest);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }

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
        restaurantRequest.status = controllerRequest.status == null ? null : RestaurantStatusView.valueOf(controllerRequest.status.name());
        BOSearchRestaurantResponse searchResponse = service.search(restaurantRequest);
        return Response.bean(searchResponse);
    }
}
