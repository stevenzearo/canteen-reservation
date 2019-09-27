package app.canteen.service;

import app.canteen.service.reservation.CombineSearchReservationRequest;
import app.canteen.service.reservation.CombineSearchReservationResponse;
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
public class CombineSearchReservationService {
    @Inject
    BOReservationWebService reservationWebService;

    @Inject
    BOUserWebService userWebService;

    @Inject
    BORestaurantWebService restaurantWebService;

    private CombineSearchReservationResponse.Reservation transfer(BOSearchReservationResponse.Reservation reservation) {
        CombineSearchReservationResponse.Reservation boReservation = new CombineSearchReservationResponse.Reservation();
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

    public CombineSearchReservationResponse search(CombineSearchReservationRequest request) {
        BOSearchReservationRequest reservationRequest = new BOSearchReservationRequest();
        reservationRequest.skip = request.reservationSkip;
        reservationRequest.limit = request.reservationLimit;
        if (request.reservingDate != null)
            reservationRequest.reservingTimeStart = request.reservingDate;
        BOSearchReservationResponse searchResponse = reservationWebService.search(reservationRequest);
        CombineSearchReservationResponse boSearchResponse = new CombineSearchReservationResponse();
        boSearchResponse.total = searchResponse.total;
        boSearchResponse.reservationList = searchResponse.reservationList.stream().map(this::transfer).collect(Collectors.toList());
        return boSearchResponse;
    }

    public CombineSearchReservationResponse searchByUserName(CombineSearchReservationRequest request) {
        CombineSearchReservationResponse boSearchResponse = new CombineSearchReservationResponse();
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
                    boSearchResponse.reservationList.addAll(search.reservationList.stream().map(this::transfer).collect(Collectors.toList()));
                    boSearchResponse.total += search.total;
                }
            });
        }
        boSearchResponse.userTotal = userResponse.total;
        return boSearchResponse;
    }

    public CombineSearchReservationResponse searchByRestaurantName(CombineSearchReservationRequest request) {
        CombineSearchReservationResponse boSearchResponse = new CombineSearchReservationResponse();
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
                    boSearchResponse.reservationList.addAll(search.reservationList.stream().map(this::transfer).collect(Collectors.toList()));
                }
            });
        }
        boSearchResponse.restaurantTotal = restaurantResponse.total;
        return boSearchResponse;
    }

    public CombineSearchReservationResponse searchByUserNameAndRestaurantName(CombineSearchReservationRequest request) {
        CombineSearchReservationResponse searchByUserName = searchByUserName(request);
        CombineSearchReservationResponse searchByRestaurantName = searchByRestaurantName(request);
        CombineSearchReservationResponse response = new CombineSearchReservationResponse();
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
                        response.reservationList.addAll(reservationResponse.reservationList.stream().map(this::transfer).collect(Collectors.toList()));
                    }
                });
            });
        }
        return response;
    }
}
