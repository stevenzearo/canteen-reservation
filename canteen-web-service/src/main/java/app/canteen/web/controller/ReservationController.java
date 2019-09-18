package app.canteen.web.controller;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.ReservationStatus;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.UpdateReservationRequest;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.RestaurantView;
import com.fasterxml.jackson.core.type.TypeReference;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.time.zone.ZoneOffsetTransition;
import java.util.List;
import java.util.Map;

import static core.framework.internal.json.JSONMapper.OBJECT_MAPPER;

/**
 * @author steve
 */
public class ReservationController {
    @Inject
    ReservationWebService reservationWebService;

    @Inject
    RestaurantWebService restaurantWebService;

    public Response reserve(Request request) {
        Map<String, String> paramMap = request.queryParams();
        ReserveRequest reserveRequest = new ReserveRequest();
        reserveRequest.restaurantId = paramMap.get("restaurant_id");
        reserveRequest.userId = Long.valueOf(paramMap.get("user_id"));
        reserveRequest.status = ReservationStatus.OK;
        reserveRequest.eatTime = JSON.fromJSON(ZonedDateTime.class, paramMap.get("eat_time"));
        reserveRequest.reserveTime = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_time"));
        reserveRequest.amount = Double.valueOf(paramMap.get("amount"));
        try {
            reserveRequest.mealIdList = OBJECT_MAPPER.readValue(paramMap.get("meal_id_list"), new TypeReference<List<String>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReservationView reserve = reservationWebService.reserve(reserveRequest);
        return Response.bean(reserve);
    }

    public Response cancel(Request request) {
        Map<String, String> paramMap = request.queryParams();
        String restaurantId = paramMap.get("restaurant_id");
        RestaurantView restaurantView = restaurantWebService.get(restaurantId);
        ReservationView reservationView = reservationWebService.get(paramMap.get("reservation_id"));
        boolean cancelStatus = false;
        if (reservationView.reserveTime.plusMinutes(10).isBefore(restaurantView.reserveDeadline)) {
            UpdateReservationRequest updateRequest = new UpdateReservationRequest();
            updateRequest.status = ReservationStatus.CANCEL;
            reservationWebService.update(reservationView.id, updateRequest);
            cancelStatus = true;
        }
        return Response.bean(cancelStatus);
    }
}
