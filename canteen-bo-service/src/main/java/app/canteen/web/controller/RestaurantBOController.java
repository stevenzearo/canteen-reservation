package app.canteen.web.controller;

import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.BOCreateRestaurantRequest;
import app.restaurant.api.restaurant.BOUpdateRestaurantRequest;
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
        BOCreateRestaurantRequest BOCreateRestaurantRequest = new BOCreateRestaurantRequest();
        Map<String, String> paramMap = request.formParams();
        BOCreateRestaurantRequest.name = paramMap.get("name");
        BOCreateRestaurantRequest.address = paramMap.get("address");
        BOCreateRestaurantRequest.phone = paramMap.get("phone");
        BOCreateRestaurantRequest.reservingDeadline = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserving_deadline"));
        return Response.bean(service.create(BOCreateRestaurantRequest)); // should return a page, return text for test.
    }

    public Response updateDeadline(Request request) {
        Map<String, String> paramMap = request.formParams();
        String id = paramMap.get("id");
        BOUpdateRestaurantRequest BOUpdateRestaurantRequest = new BOUpdateRestaurantRequest();
        BOUpdateRestaurantRequest.reservingDeadline = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_deadline"));
        service.update(id, BOUpdateRestaurantRequest);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }
}