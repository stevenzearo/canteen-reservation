package app.canteen.web.ajax;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.user.api.BOUserWebService;
import app.user.api.UserWebService;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author steve
 */
public class ReservationBOAJAXController {
    @Inject
    ReservationWebService reservationWebService;

    @Inject
    BOUserWebService userWebService;

    @Inject
    RestaurantWebService restaurantWebService;

    public Response searchListByPage(Request request) {
        Map<String, String> paramMap = request.queryParams();
        Integer pageNumber = Integer.valueOf(paramMap.get("page_number"));
        Integer pageSize = Integer.valueOf(paramMap.get("page_size"));
        Integer skip = 0;
        Integer limit = 10;
        if (pageNumber > 0 && pageSize > 0) {
            skip = (pageNumber - 1) * pageSize;
            limit = pageSize;
        }
        SearchReservationRequest searchRequest = new SearchReservationRequest();
        searchRequest.limit = limit;
        searchRequest.skip = skip;
        return Response.bean(reservationWebService.searchListByConditions(searchRequest));
    }

    // search reservation via username/restaurant name/booking date
    public Response searchListByConditionsAndPage(Request request) {
        Map<String, String> paramMap = request.queryParams();
        String userName = paramMap.get("user_name");
        String restaurantName = paramMap.get("restaurant_name");
        ZonedDateTime searchTime = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_date"));
        final List<Long> userIdList = Lists.newArrayList();
        final List<String> restaurantIdList = Lists.newArrayList();
        Long userTotal = 0L;
        Long restaurantTotal = 0L;
        if (!Strings.isBlank(userName)) {
            SearchUserRequest userRequest = new SearchUserRequest();
            userRequest.skip = 0;
            userRequest.limit = 10;
            userRequest.name = userName;
            SearchUserResponse userResponse = userWebService.search(userRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
            userTotal = userResponse.total;
            if (userResponse.userList.size() > 0)
                userResponse.userList.forEach(userView -> userIdList.add(userView.id));
        }
        if (!Strings.isBlank(restaurantName)) {
            SearchRestaurantRequest restaurantRequest = new SearchRestaurantRequest();
            restaurantRequest.name = restaurantName;
            restaurantRequest.skip = 0;
            restaurantRequest.limit = 10;
            app.restaurant.api.restaurant.SearchResponse restaurantResponse = restaurantWebService.searchListByConditions(restaurantRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
            restaurantTotal = restaurantResponse.total;
            if (restaurantResponse.restaurantViewList.size() > 0)
                restaurantResponse.restaurantViewList.forEach(restaurantView -> restaurantIdList.add(restaurantView.id));
        }
        List<ReservationView> reservationViewList = Lists.newArrayList();
        AtomicReference<Long> reservationTotal = new AtomicReference<>(0L);
        SearchReservationRequest reservationRequest = new SearchReservationRequest();
        reservationRequest.reserveTimeEqualLaterThan = searchTime;
        if (!userIdList.isEmpty()) {
            userIdList.forEach(userId -> {
                reservationRequest.userId = userId;
                if (!restaurantIdList.isEmpty())
                    restaurantIdList.forEach(restaurantId -> {
                        reservationRequest.restaurantId = restaurantId;
                        reservationRequest.skip = 0;
                        reservationRequest.limit = 10;
                        SearchReservationResponse reservationResponse = reservationWebService.searchListByConditions(reservationRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
                        reservationTotal.updateAndGet(v -> v + reservationResponse.total);
                        reservationViewList.addAll(reservationResponse.reservationViewList);
                    });
            });
        }
        SearchReservationResponse reservationResponse = new SearchReservationResponse();
        reservationResponse.total = reservationTotal.get();
        reservationResponse.reservationViewList = reservationViewList;
        return Response.bean(Map.of("user_total", userTotal, "restaurant_total", restaurantTotal, "reservation_result", reservationResponse));
    }

}
