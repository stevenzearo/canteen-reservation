package app.canteen.web.controller;

import app.canteen.web.controller.reservation.CancellingControllerRequest;
import app.canteen.web.controller.reservation.ReservationReservingControllerRequest;
import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReserveResponse;
import app.reservation.api.reservation.ReservingRequest;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.GetRestaurantResponse;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        ReservationReservingControllerRequest controllerReservingRequest = request.bean(ReservationReservingControllerRequest.class);
        ReservingRequest reservingRequest = new ReservingRequest();
        reservingRequest.restaurantId = controllerReservingRequest.restaurantId;
        reservingRequest.amount = controllerReservingRequest.amount;
        reservingRequest.eatingTime = controllerReservingRequest.eatingTime;
        reservingRequest.reservingDeadline = controllerReservingRequest.reservingDeadline;
        reservingRequest.mealIdList = controllerReservingRequest.mealIdList;
        ReserveResponse reserveResponse = reservationWebService.reserve(controllerReservingRequest.userId, reservingRequest);
        return Response.bean(reserveResponse); // should return a page, return a bean for test.
    }

    public Response cancel(Request request) {
        CancellingControllerRequest cancellingControllerRequest = request.bean(CancellingControllerRequest.class);
        GetRestaurantResponse restaurantResponse = restaurantWebService.get(cancellingControllerRequest.restaurantId);
        GetReservationResponse reservationResponse = reservationWebService.get(cancellingControllerRequest.userId, cancellingControllerRequest.reservationId);
        String cancelStatus = "FAILED";
        logger.warn(Strings.format("cancelling reservation, id = {}", cancellingControllerRequest.reservationId));
        if (reservationResponse.reservingTime.plusMinutes(10).isBefore(restaurantResponse.reservingDeadline)) {
            reservationWebService.cancel(cancellingControllerRequest.userId, reservationResponse.id);
            cancelStatus = "SUCCESS";
        }
        return Response.text(cancelStatus); // should return a page, return text for test.
    }
}
