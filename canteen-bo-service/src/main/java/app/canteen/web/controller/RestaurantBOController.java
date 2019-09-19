package app.canteen.web.controller;

import app.restaurant.api.RestaurantWebService;
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
    RestaurantWebService service;

    public Response create(Request request) {
        CreateRestaurantRequest createRestaurantRequest = new CreateRestaurantRequest();
        Map<String, String> paramMap = request.queryParams();
        createRestaurantRequest.name = paramMap.get("name");
        createRestaurantRequest.address = paramMap.get("address");
        createRestaurantRequest.phone = paramMap.get("phone");
        createRestaurantRequest.reserveDeadline = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_deadline"));
        return Response.bean(service.create(createRestaurantRequest));
    }

    public Response updateDeadline(Request request) {
        Map<String, String> paramMap = request.queryParams();
        String id = paramMap.get("id");
        UpdateRestaurantRequest updateRestaurantRequest = new UpdateRestaurantRequest();
        updateRestaurantRequest.reserveDeadline = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_deadline"));
        service.update(id, updateRestaurantRequest);
        return Response.text("SUCCESS");
    }
}