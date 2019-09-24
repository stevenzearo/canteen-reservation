package app.canteen.web.controller;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservationStatusView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.ReserveResponse;
import app.reservation.api.reservation.UpdateReservationRequest;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.GetRestaurantResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static core.framework.internal.json.JSONMapper.OBJECT_MAPPER;

/**
 * @author steve
 */
public class ReservationController {
    private final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    @Inject
    ReservationWebService reservationWebService;
    @Inject
    RestaurantWebService restaurantWebService;

    public Response reserve(Request request) {
        Map<String, String> paramMap = request.formParams();
        ReserveRequest reserveRequest = new ReserveRequest();
        reserveRequest.restaurantId = paramMap.get("restaurant_id");
        Long userId = Long.valueOf(paramMap.get("user_id"));
        reserveRequest.status = ReservationStatusView.OK;
        reserveRequest.reservingTime = ZonedDateTime.now();
        reserveRequest.eatingTime = JSON.fromJSON(ZonedDateTime.class, paramMap.get("eating_time"));
        reserveRequest.reservingDeadline = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserving_deadline"));
        reserveRequest.amount = Double.valueOf(paramMap.get("amount"));
        try {
            reserveRequest.mealIdList = OBJECT_MAPPER.readValue(paramMap.get("meal_id_list"), new ListTypeReference());
        } catch (IOException e) {
            logger.error("OBJECT MAPPER ERROR");
        }
        ReserveResponse response = reservationWebService.reserve(userId, reserveRequest);
        return Response.bean(response); // should return a module, return a bean for test.
    }

    public Response cancel(Request request) {
        Map<String, String> paramMap = request.formParams();
        String restaurantId = paramMap.get("restaurant_id");
        Long userId = Long.valueOf(paramMap.get("user_id"));
        GetRestaurantResponse restaurantResponse = restaurantWebService.get(restaurantId);
        GetReservationResponse reservationResponse = reservationWebService.get(userId, paramMap.get("reservation_id"));
        String cancelStatus = "FAILED";
        if (reservationResponse.reservingTime.plusMinutes(10).isBefore(restaurantResponse.reservingDeadline)) {
            UpdateReservationRequest updateRequest = new UpdateReservationRequest();
            updateRequest.status = ReservationStatusView.CANCEL;
            reservationWebService.update(userId, reservationResponse.id, updateRequest);
            cancelStatus = "SUCCESS";
        }
        return Response.text(cancelStatus); // should return a module, return text for test.
    }

    private static class ListTypeReference extends TypeReference<List<String>> {
    }
}
