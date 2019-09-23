package app.canteen.service.reservation;

import app.reservation.api.BOReservationWebService;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.user.api.BOUserWebService;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import core.framework.inject.Inject;
import core.framework.util.Lists;

import java.util.stream.Collectors;

/**
 * @author steve
 */
public class BOSearchReservationService {
    @Inject
    BOReservationWebService reservationWebService;

    @Inject
    BOUserWebService userWebService;

    @Inject
    RestaurantWebService restaurantWebService;

    // users -> db, notification -> db, restaurant
    public BOSearchReservationResponse search(BOSearchReservationRequest request) {
        SearchReservationRequest reservationRequest = new SearchReservationRequest();
        reservationRequest.skip = request.reservationSkip;
        reservationRequest.limit = request.reservationLimit;
        if (request.reservingDate != null)
            reservationRequest.reservingTimeStart = request.reservingDate;
        SearchReservationResponse searchResponse = reservationWebService.search(reservationRequest);
        BOSearchReservationResponse boSearchResponse = new BOSearchReservationResponse();
        boSearchResponse.total = searchResponse.total;
        boSearchResponse.reservationList = searchResponse.reservationList;
        return boSearchResponse;
    }

    public BOSearchReservationResponse searchByUserName(BOSearchReservationRequest request) {
        BOSearchReservationResponse boSearchResponse = new BOSearchReservationResponse();
        SearchUserRequest userRequest = new SearchUserRequest();
        userRequest.skip = request.userSkip;
        userRequest.limit = request.userLimit;
        userRequest.name = request.userName;
        SearchUserResponse userResponse = userWebService.search(userRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
        boSearchResponse.userTotal = userResponse.total;
        boSearchResponse.userIdList = userResponse.userList.stream().map(userView -> userView.id).collect(Collectors.toList());
        SearchReservationRequest searchReservationRequest = new SearchReservationRequest();
        boSearchResponse.reservationList = Lists.newArrayList();
        boSearchResponse.total = 0L;
        if (userResponse.userList.size() > 0) {
            userResponse.userList.forEach(userView -> {
                searchReservationRequest.userId = userView.id;
                SearchReservationResponse search = reservationWebService.search(searchReservationRequest);
                if (search.total > 0) {
                    boSearchResponse.reservationList.addAll(search.reservationList);
                    boSearchResponse.total += search.total;
                }
            });
        }
        boSearchResponse.userTotal = userResponse.total;
        return boSearchResponse;
    }

    public BOSearchReservationResponse searchByRestaurantName(BOSearchReservationRequest request) {
        BOSearchReservationResponse boSearchResponse = new BOSearchReservationResponse();
        SearchRestaurantRequest restaurantRequest = new SearchRestaurantRequest();

        restaurantRequest.name = request.restaurantName;
        restaurantRequest.skip = request.restaurantSkip;
        restaurantRequest.limit = request.restaurantLimit;
        app.restaurant.api.restaurant.SearchResponse restaurantResponse = restaurantWebService.search(restaurantRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
        boSearchResponse.restaurantTotal = restaurantResponse.total;
        boSearchResponse.restaurantIdList = restaurantResponse.restaurantList.stream().map(restaurantView -> restaurantView.id).collect(Collectors.toList());
        SearchReservationRequest searchReservationRequest = new SearchReservationRequest();
        boSearchResponse.reservationList = Lists.newArrayList();
        boSearchResponse.total = 0L;
        if (restaurantResponse.restaurantList.size() > 0) {
            restaurantResponse.restaurantList.forEach(restaurantView -> {
                searchReservationRequest.restaurantId = restaurantView.id;
                SearchReservationResponse search = reservationWebService.search(searchReservationRequest);
                if (search.total > 0) {
                    boSearchResponse.total += search.total;
                    boSearchResponse.reservationList.addAll(search.reservationList);
                }
            });
        }
        boSearchResponse.restaurantTotal = restaurantResponse.total;
        return boSearchResponse;
    }

    public BOSearchReservationResponse searchByUserNameAndRestaurantName(BOSearchReservationRequest request) {
        BOSearchReservationResponse searchByUserName = searchByUserName(request);
        BOSearchReservationResponse searchByRestaurantName = searchByRestaurantName(request);
        BOSearchReservationResponse response = new BOSearchReservationResponse();
        response.reservationList = Lists.newArrayList();
        response.userTotal = searchByUserName.userTotal;
        response.userIdList = searchByUserName.userIdList;
        response.restaurantTotal = searchByRestaurantName.restaurantTotal;
        response.restaurantIdList = searchByRestaurantName.restaurantIdList;
        response.total = 0L;
        response.reservationList = Lists.newArrayList();
        if (searchByUserName.userTotal > 0 && searchByRestaurantName.restaurantTotal > 0) {
            SearchReservationRequest reservationRequest = new SearchReservationRequest();
            response.userIdList.forEach(userId -> {
                reservationRequest.userId = userId;
                response.restaurantIdList.forEach(restaurantId -> {
                    reservationRequest.restaurantId = restaurantId;
                    reservationRequest.skip = request.reservationSkip;
                    reservationRequest.limit = request.reservationLimit;
                    SearchReservationResponse reservationResponse = reservationWebService.search(reservationRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
                    if (reservationResponse.total > 0) {
                        response.total += reservationResponse.total;
                        response.reservationList.addAll(reservationResponse.reservationList);
                    }
                });
            });
        }
        return response;
    }
}
