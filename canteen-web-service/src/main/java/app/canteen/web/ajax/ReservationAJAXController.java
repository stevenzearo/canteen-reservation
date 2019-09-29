package app.canteen.web.ajax;

import app.canteen.web.ajax.reservation.CancellingReservationAJAXRequest;
import app.canteen.web.ajax.reservation.ReservingReservationAJAXRequest;
import app.canteen.web.ajax.reservation.SearchReservationAJAXRequest;
import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservingRequest;
import app.reservation.api.reservation.ReservingResponse;
import app.reservation.api.reservation.SearchReservationRequest;
import app.restaurant.api.MealWebService;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.meal.GetMealResponse;
import app.restaurant.api.restaurant.GetRestaurantResponse;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author steve
 */
public class ReservationAJAXController {
    private final Logger logger = LoggerFactory.getLogger(ReservationAJAXController.class);

    @Inject
    ReservationWebService reservationWebService;

    @Inject
    RestaurantWebService restaurantWebService;

    @Inject
    MealWebService mealWebService;

    public Response reserve(Request request) {
        Long userId = Long.valueOf(request.session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first")));
        ReservingReservationAJAXRequest controllerReservingRequest = request.bean(ReservingReservationAJAXRequest.class);
        ReservingRequest reservingRequest = new ReservingRequest();
        GetRestaurantResponse restaurantResponse = restaurantWebService.get(controllerReservingRequest.restaurantId);
        reservingRequest.restaurantId = restaurantResponse.id;
        reservingRequest.restaurantName = restaurantResponse.name;
        reservingRequest.reservingDeadline = restaurantResponse.reservingDeadline;
        reservingRequest.amount = 0d;
        reservingRequest.eatingTime = controllerReservingRequest.eatingTime;
        reservingRequest.meals = Lists.newArrayList();
        List<String> mealIdList = controllerReservingRequest.mealIdList;
        mealIdList.forEach(mealId -> {
            GetMealResponse mealResponse = mealWebService.get(reservingRequest.restaurantId, mealId);
            ReservingRequest.Meal reservingMeal = new ReservingRequest.Meal();
            reservingMeal.id = mealResponse.id;
            reservingMeal.name = mealResponse.name;
            reservingMeal.price = mealResponse.price;
            reservingRequest.amount += mealResponse.price;
            reservingRequest.meals.add(reservingMeal);
        });
        ReservingResponse reservingResponse = reservationWebService.reserve(userId, reservingRequest);
        return Response.bean(reservingResponse);
    }

    public Response cancel(Request request) {
        Long userId = Long.valueOf(request.session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first")));
        CancellingReservationAJAXRequest cancellingReservationAJAXRequest = request.bean(CancellingReservationAJAXRequest.class);
        GetRestaurantResponse restaurantResponse = restaurantWebService.get(cancellingReservationAJAXRequest.restaurantId);
        GetReservationResponse reservationResponse = reservationWebService.get(userId, cancellingReservationAJAXRequest.reservationId);
        String cancelStatus = "FAILED";
        logger.warn(Strings.format("cancelling reservation, id = {}", cancellingReservationAJAXRequest.reservationId));
        if (reservationResponse.reservingTime.plusMinutes(10).isBefore(restaurantResponse.reservingDeadline)) {
            reservationWebService.cancel(userId, reservationResponse.id);
            cancelStatus = "SUCCESS";
        }
        return Response.text(cancelStatus);
    }

    public Response search(Request request) {
        Long userId = Long.valueOf(request.session().get("user_id").orElseThrow(() -> new UnauthorizedException("please login first")));
        SearchReservationAJAXRequest controllerRequest = request.bean(SearchReservationAJAXRequest.class);
        SearchReservationRequest reservationRequest = new SearchReservationRequest();
        reservationRequest.skip = controllerRequest.skip;
        reservationRequest.limit = controllerRequest.limit;
        reservationRequest.reservingTimeStart = controllerRequest.reservingTimeStart;
        reservationRequest.reservingTimeEnd = controllerRequest.reservingTimeEnd;
        return Response.bean(reservationWebService.search(userId, reservationRequest));
    }
}
