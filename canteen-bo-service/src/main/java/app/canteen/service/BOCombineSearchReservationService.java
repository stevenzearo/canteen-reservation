package app.canteen.service;

import app.canteen.service.reservation.BOCombineSearchReservationRequest;
import app.canteen.service.reservation.BOCombineSearchReservationResponse;
import app.reservation.api.BOReservationWebService;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.BOSearchRestaurantRequest;
import app.restaurant.api.restaurant.BOSearchRestaurantResponse;
import app.user.api.BOUserWebService;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import core.framework.inject.Inject;
import core.framework.util.Lists;

import java.util.stream.Collectors;

/**
 * @author steve
 */
public class BOCombineSearchReservationService {
    @Inject
    BOReservationWebService reservationWebService;

    @Inject
    BOUserWebService userWebService;

    @Inject
    BORestaurantWebService restaurantWebService;

    private static BOCombineSearchReservationResponse.ReservationView transfer(BOSearchReservationResponse.Reservation reservation) {
        BOCombineSearchReservationResponse.ReservationView boReservation = new BOCombineSearchReservationResponse.ReservationView();
        boReservation.id = reservation.id;
        boReservation.amount = reservation.amount;
        boReservation.reservingTime = reservation.reservingTime;
        boReservation.eatingTime = reservation.eatingTime;
        boReservation.status = reservation.status;
        boReservation.userId = reservation.userId;
        boReservation.restaurantId = reservation.restaurantId;
        boReservation.mealIdList = reservation.mealIdList;
        return boReservation;
    }

    public BOCombineSearchReservationResponse search(BOCombineSearchReservationRequest request) {
        BOSearchReservationRequest reservationRequest = new BOSearchReservationRequest();
        reservationRequest.skip = request.reservationSkip;
        reservationRequest.limit = request.reservationLimit;
        if (request.reservingDate != null)
            reservationRequest.reservingTimeStart = request.reservingDate;
        BOSearchReservationResponse searchResponse = reservationWebService.search(reservationRequest);
        BOCombineSearchReservationResponse boSearchResponse = new BOCombineSearchReservationResponse();
        boSearchResponse.total = searchResponse.total;
        boSearchResponse.reservationList = searchResponse.reservationList.stream().map(BOCombineSearchReservationService::transfer).collect(Collectors.toList());
        return boSearchResponse;
    }

    public BOCombineSearchReservationResponse searchByUserName(BOCombineSearchReservationRequest request) {
        BOCombineSearchReservationResponse boSearchResponse = new BOCombineSearchReservationResponse();
        BOSearchUserRequest userRequest = new BOSearchUserRequest();
        userRequest.skip = request.userSkip;
        userRequest.limit = request.userLimit;
        userRequest.name = request.userName;
        BOSearchUserResponse userResponse = userWebService.search(userRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
        boSearchResponse.userTotal = userResponse.total;
        boSearchResponse.userIdList = userResponse.users.stream().map(userView -> userView.id).collect(Collectors.toList());
        boSearchResponse.reservationList = Lists.newArrayList();
        boSearchResponse.total = 0L;
        if (userResponse.users.size() > 0) {
            BOSearchReservationRequest searchReservationRequest = new BOSearchReservationRequest();
            userResponse.users.forEach(userView -> {
                searchReservationRequest.userId = userView.id;
                BOSearchReservationResponse search = reservationWebService.search(searchReservationRequest);
                if (reservationWebService.search(searchReservationRequest).total > 0) {
                    boSearchResponse.reservationList.addAll(search.reservationList.stream().map(BOCombineSearchReservationService::transfer).collect(Collectors.toList()));
                    boSearchResponse.total += search.total;
                }
            });
        }
        boSearchResponse.userTotal = userResponse.total;
        return boSearchResponse;
    }

    public BOCombineSearchReservationResponse searchByRestaurantName(BOCombineSearchReservationRequest request) {
        BOCombineSearchReservationResponse boSearchResponse = new BOCombineSearchReservationResponse();
        BOSearchRestaurantRequest restaurantRequest = new BOSearchRestaurantRequest();
        restaurantRequest.name = request.restaurantName;
        restaurantRequest.skip = request.restaurantSkip;
        restaurantRequest.limit = request.restaurantLimit;
        BOSearchRestaurantResponse restaurantResponse = restaurantWebService.search(restaurantRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
        boSearchResponse.restaurantTotal = restaurantResponse.total;
        boSearchResponse.restaurantIdList = restaurantResponse.restaurants.stream().map(restaurantView -> restaurantView.id).collect(Collectors.toList());
        boSearchResponse.reservationList = Lists.newArrayList();
        boSearchResponse.total = 0L;
        if (restaurantResponse.restaurants.size() > 0) {
            BOSearchReservationRequest searchReservationRequest = new BOSearchReservationRequest();
            restaurantResponse.restaurants.forEach(restaurantView -> {
                searchReservationRequest.restaurantId = restaurantView.id;
                BOSearchReservationResponse search = reservationWebService.search(searchReservationRequest);
                if (search.total > 0) {
                    boSearchResponse.total += search.total;
                    boSearchResponse.reservationList.addAll(search.reservationList.stream().map(BOCombineSearchReservationService::transfer).collect(Collectors.toList()));
                }
            });
        }
        boSearchResponse.restaurantTotal = restaurantResponse.total;
        return boSearchResponse;
    }

    public BOCombineSearchReservationResponse searchByUserNameAndRestaurantName(BOCombineSearchReservationRequest request) {
        BOCombineSearchReservationResponse searchByUserName = searchByUserName(request);
        BOCombineSearchReservationResponse searchByRestaurantName = searchByRestaurantName(request);
        BOCombineSearchReservationResponse response = new BOCombineSearchReservationResponse();
        response.reservationList = Lists.newArrayList();
        response.userTotal = searchByUserName.userTotal;
        response.userIdList = searchByUserName.userIdList;
        response.restaurantTotal = searchByRestaurantName.restaurantTotal;
        response.restaurantIdList = searchByRestaurantName.restaurantIdList;
        response.total = 0L;
        response.reservationList = Lists.newArrayList();
        if (searchByUserName.userTotal > 0 && searchByRestaurantName.restaurantTotal > 0) {
            BOSearchReservationRequest reservationRequest = new BOSearchReservationRequest();
            response.userIdList.forEach(userId -> {
                reservationRequest.userId = userId;
                response.restaurantIdList.forEach(restaurantId -> {
                    reservationRequest.restaurantId = restaurantId;
                    reservationRequest.skip = request.reservationSkip;
                    reservationRequest.limit = request.reservationLimit;
                    BOSearchReservationResponse reservationResponse = reservationWebService.search(reservationRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
                    if (reservationResponse.total > 0) {
                        response.total += reservationResponse.total;
                        response.reservationList.addAll(reservationResponse.reservationList.stream().map(BOCombineSearchReservationService::transfer).collect(Collectors.toList()));
                    }
                });
            });
        }
        return response;
    }
}
