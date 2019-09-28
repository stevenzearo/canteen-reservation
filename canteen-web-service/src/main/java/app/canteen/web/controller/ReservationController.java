package app.canteen.web.controller;

import app.canteen.web.controller.reservation.CancellingReservationRequest;
import app.canteen.web.controller.reservation.ReservingReservationRequest;
import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReserveResponse;
import app.reservation.api.reservation.ReservingRequest;
import app.restaurant.api.MealWebService;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.meal.GetMealResponse;
import app.restaurant.api.restaurant.GetRestaurantResponse;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author steve
 */
public class ReservationController {
    private final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Inject
    ReservationWebService reservationWebService;

    @Inject
    RestaurantWebService restaurantWebService;

    @Inject
    MealWebService mealWebService;

    public Response reserve(Request request) {
        ReservingReservationRequest controllerReservingRequest = request.bean(ReservingReservationRequest.class);
        app.reservation.api.reservation.ReservingRequest reservingRequest = new app.reservation.api.reservation.ReservingRequest();
        reservingRequest.restaurant = new ReservingRequest.Restaurant();
        GetRestaurantResponse restaurantResponse = restaurantWebService.get(controllerReservingRequest.restaurantId);
        reservingRequest.restaurant.id = restaurantResponse.id;
        reservingRequest.restaurant.name = restaurantResponse.name;
        reservingRequest.restaurant.phone = restaurantResponse.phone;
        reservingRequest.restaurant.address = restaurantResponse.address;
        reservingRequest.restaurant.reservingDeadline = restaurantResponse.reservingDeadline;
        reservingRequest.amount = controllerReservingRequest.amount;
        reservingRequest.eatingTime = controllerReservingRequest.eatingTime;
        reservingRequest.mealList = Lists.newArrayList();
        List<String> mealIdList = controllerReservingRequest.mealIdList;
        mealIdList.forEach(mealId -> {
            GetMealResponse mealResponse = mealWebService.get(reservingRequest.restaurant.id, mealId);
            ReservingRequest.Meal reservingMeal = new ReservingRequest.Meal();
            reservingMeal.id = mealResponse.id;
            reservingMeal.name = mealResponse.name;
            reservingMeal.price = mealResponse.price;
            reservingRequest.mealList.add(reservingMeal);
        });
        ReserveResponse reserveResponse = reservationWebService.reserve(controllerReservingRequest.userId, reservingRequest);
        return Response.bean(reserveResponse); // should return a page, return a bean for test.
    }

    public Response cancel(Request request) {
        CancellingReservationRequest cancellingReservationRequest = request.bean(CancellingReservationRequest.class);
        GetRestaurantResponse restaurantResponse = restaurantWebService.get(cancellingReservationRequest.restaurantId);
        GetReservationResponse reservationResponse = reservationWebService.get(cancellingReservationRequest.userId, cancellingReservationRequest.reservationId);
        String cancelStatus = "FAILED";
        logger.warn(Strings.format("cancelling reservation, id = {}", cancellingReservationRequest.reservationId));
        if (reservationResponse.reservingTime.plusMinutes(10).isBefore(restaurantResponse.reservingDeadline)) {
            reservationWebService.cancel(cancellingReservationRequest.userId, reservationResponse.id);
            cancelStatus = "SUCCESS";
        }
        return Response.text(cancelStatus); // should return a page, return text for test.
    }
}
