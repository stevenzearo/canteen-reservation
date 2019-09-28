package app.canteen.web.controller;

import app.canteen.web.controller.restaurant.CreateRestaurantRequest;
import app.canteen.web.controller.restaurant.UpdateRestaurantRequest;
import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.BOCreateRestaurantRequest;
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
public class RestaurantController {
    @Inject
    BORestaurantWebService service;

    public Response create(Request request) {
        CreateRestaurantRequest controllerRequest = request.bean(CreateRestaurantRequest.class);
        BOCreateRestaurantRequest createRequest = new BOCreateRestaurantRequest();
        createRequest.name = controllerRequest.name;
        createRequest.phone = controllerRequest.phone;
        createRequest.address = controllerRequest.address;
        createRequest.reservingDeadline = controllerRequest.reservingDeadline;
        return Response.bean(service.create(createRequest)); // should return a page, return text for test.
    }

    public Response updateDeadline(Request request) {
        UpdateRestaurantRequest controllerRequest = request.bean(UpdateRestaurantRequest.class);
        BOUpdateRestaurantRequest updateRestaurantRequest = new BOUpdateRestaurantRequest();
        updateRestaurantRequest.name = controllerRequest.name;
        updateRestaurantRequest.phone = controllerRequest.phone;
        updateRestaurantRequest.address = controllerRequest.address;
        updateRestaurantRequest.status = RestaurantStatusView.valueOf(controllerRequest.status.name());
        updateRestaurantRequest.reservingDeadline = controllerRequest.reservingDeadline;
        Map<String, String> paramMap = request.queryParams();
        String id = paramMap.get("id");
        if (Strings.isBlank(id)) throw new BadRequestException("id can not be blank");
        service.update(id, updateRestaurantRequest);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }
}