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
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;
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
        String restaurantId;
        Long userId;
        ZonedDateTime eatingTime;
        ZonedDateTime reservingDeadline;
        Double amount;
        List<String> mealIdList;
        try {
            restaurantId = paramMap.get("restaurant_id");
            userId = Long.valueOf(paramMap.get("user_id"));
            eatingTime = ZonedDateTime.parse(paramMap.get("eating_time"));
            reservingDeadline = ZonedDateTime.parse(paramMap.get("reserving_deadline"));
            amount = Double.valueOf(paramMap.get("amount"));
            mealIdList = OBJECT_MAPPER.readValue(paramMap.get("meal_id_list"), new ListTypeReference());
            if (Strings.isBlank(restaurantId)) throw new BadRequestException("restaurant id can not be blank");
        } catch (IOException e) {
            logger.error("OBJECT MAPPER ERROR");
            throw new BadRequestException("invalid meal id list");
        } catch (NumberFormatException e) {
            throw new BadRequestException("invalid user id or amount");
        }
        reserveRequest.restaurantId = restaurantId;
        reserveRequest.reservingTime = ZonedDateTime.now();
        reserveRequest.status = ReservationStatusView.OK;
        reserveRequest.amount = amount;
        reserveRequest.eatingTime = eatingTime;
        reserveRequest.reservingDeadline = reservingDeadline;
        reserveRequest.mealIdList = mealIdList;
        ReserveResponse reserveResponse = reservationWebService.reserve(userId, reserveRequest);
        return Response.bean(reserveResponse); // should return a page, return a bean for test.
    }

    public Response cancel(Request request) {
        Map<String, String> paramMap = request.formParams();
        Long userId;
        String restaurantId;
        String reservationId;
        try {
            userId = Long.valueOf(paramMap.get("user_id"));
            restaurantId = paramMap.get("restaurant_id");
            reservationId = paramMap.get("reservation_id");
            if (Strings.isBlank(restaurantId)) throw new BadRequestException("restaurant id can not be blank");
            if (Strings.isBlank(reservationId)) throw new BadRequestException("reservation id can not be blank");
        } catch (NumberFormatException e) {
            throw new BadRequestException("invalid user id");
        }
        GetRestaurantResponse restaurantResponse = restaurantWebService.get(restaurantId);
        GetReservationResponse reservationResponse = reservationWebService.get(userId, reservationId);
        String cancelStatus = "FAILED";
        if (reservationResponse.reservingTime.plusMinutes(10).isBefore(restaurantResponse.reservingDeadline)) {
            UpdateReservationRequest updateRequest = new UpdateReservationRequest();
            updateRequest.status = ReservationStatusView.CANCEL;
            reservationWebService.cancel(userId, reservationResponse.id);
            cancelStatus = "SUCCESS";
        }
        return Response.text(cancelStatus); // should return a page, return text for test.
    }

    private static class ListTypeReference extends TypeReference<List<String>> {
    }
}
