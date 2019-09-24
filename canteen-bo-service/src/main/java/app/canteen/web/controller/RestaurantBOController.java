package app.canteen.web.controller;

import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.CreateRestaurantRequest;
import app.restaurant.api.restaurant.UpdateRestaurantRequest;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author steve
 */
public class RestaurantBOController {
    @Inject
    BORestaurantWebService service;

    public Response create(Request request) {
        CreateRestaurantRequest createRestaurantRequest = new CreateRestaurantRequest();
        Map<String, String> paramMap = request.formParams();
        createRestaurantRequest.name = paramMap.get("name");
        createRestaurantRequest.address = paramMap.get("address");
        createRestaurantRequest.phone = paramMap.get("phone");
        createRestaurantRequest.reservingDeadline = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserving_deadline"));
        return Response.bean(service.create(createRestaurantRequest));
    }

    public Response updateDeadline(Request request) {
        Map<String, String> paramMap = request.formParams();
        String id = paramMap.get("id");
        UpdateRestaurantRequest updateRestaurantRequest = new UpdateRestaurantRequest();
        updateRestaurantRequest.reservingDeadline = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_deadline"));
        service.update(id, updateRestaurantRequest);
        return Response.text("SUCCESS"); // should return a module, return text for test.
    }
}