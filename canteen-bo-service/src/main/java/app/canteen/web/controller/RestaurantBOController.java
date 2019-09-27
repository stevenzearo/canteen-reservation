package app.canteen.web.controller;

import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.BOCreateRestaurantRequest;
import app.restaurant.api.restaurant.BOUpdateRestaurantRequest;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

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
        String name = paramMap.get("name");
        String address = paramMap.get("address");
        String phone = paramMap.get("phone");
        ZonedDateTime reservingDeadline = ZonedDateTime.parse(paramMap.get("reserving_deadline"));
        if (!Strings.isBlank(name) && !Strings.isBlank(address) && !Strings.isBlank("phone")) {
            BOCreateRestaurantRequest.name = name;
            BOCreateRestaurantRequest.address = address;
            BOCreateRestaurantRequest.phone = phone;
            BOCreateRestaurantRequest.reservingDeadline = reservingDeadline;
        } else {
            throw new BadRequestException("name and address and phone can not be blank");
        }
        return Response.bean(service.create(BOCreateRestaurantRequest)); // should return a page, return text for test.
    }

    public Response updateDeadline(Request request) {
        Map<String, String> paramMap = request.formParams();
        String id = paramMap.get("id");
        if (Strings.isBlank(id)) throw new BadRequestException("id can not be blank");
        BOUpdateRestaurantRequest BOUpdateRestaurantRequest = new BOUpdateRestaurantRequest();
        BOUpdateRestaurantRequest.reservingDeadline = ZonedDateTime.parse(paramMap.get("reserve_deadline"));
        service.update(id, BOUpdateRestaurantRequest);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }
}