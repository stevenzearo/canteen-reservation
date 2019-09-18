package app.canteen.web.ajax;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.user.api.UserWebService;
import app.user.api.user.SearchResponse;
import app.user.api.user.SearchUserRequest;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Lists;
import core.framework.util.Maps;
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
    UserWebService userWebService;

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
        String userName = paramMap.get("user_name"); // can be combined with user_page_number, user_page_size
        String restaurantName = paramMap.get("restaurant_name"); // can be combined with restaurant_page_number, restaurant_page_size
        ZonedDateTime reserveTime = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserve_date"));
        List<Long> userIdList = null;
        List<String> restaurantIdList = null;
        Long userTotal = 0L;
        Long restaurantTotal = 0L;
        if (!Strings.isBlank(userName)) {
            SearchUserRequest userRequest = new SearchUserRequest();
            userRequest.name = userName;
            userRequest.skip = 0;
            userRequest.limit = 10;
            SearchResponse userResponse = userWebService.searchListByConditions(userRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
            userTotal = userResponse.total;
            if (userResponse.userViewList.size() > 0) {
                userIdList = Lists.newArrayList();
                List<Long> finalUserIdList = userIdList;
                userResponse.userViewList.forEach(userView -> finalUserIdList.add(userView.id));
                userIdList = finalUserIdList;
            }
        }
        if (!Strings.isBlank(restaurantName)) {
            SearchRestaurantRequest restaurantRequest = new SearchRestaurantRequest();
            restaurantRequest.name = restaurantName;
            restaurantRequest.skip = 0;
            restaurantRequest.limit = 10;
            app.restaurant.api.restaurant.SearchResponse restaurantResponse = restaurantWebService.searchListByConditions(restaurantRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
            restaurantTotal = restaurantResponse.total;
            if (restaurantResponse.restaurantViewList.size() > 0) {
                restaurantIdList = Lists.newArrayList();
                List<String> finalRestaurantIdList = restaurantIdList;
                restaurantResponse.restaurantViewList.forEach(restaurantView -> finalRestaurantIdList.add(restaurantView.id));
                restaurantIdList = finalRestaurantIdList;
            }
        }
        List<ReservationView> reservationViewList = Lists.newArrayList();
        AtomicReference<Long> total = new AtomicReference<>(0L);
        SearchReservationRequest reservationRequest = new SearchReservationRequest();
        reservationRequest.reserveTime = reserveTime;
        if (userIdList != null) {
            List<String> finalRestaurantIdList1 = restaurantIdList;
            userIdList.forEach(userId -> {
                reservationRequest.userId = userId;
                if (finalRestaurantIdList1 != null) {
                    finalRestaurantIdList1.forEach(restaurantId -> {
                        reservationRequest.restaurantId = restaurantId;
                        reservationRequest.skip = 0;
                        reservationRequest.limit = 10;
                        SearchReservationResponse reservationResponse = reservationWebService.searchListByConditions(reservationRequest); // this search result size can be very big, restricted by skip(0) and limit(10)
                        total.updateAndGet(v -> v + reservationResponse.total);
                        reservationViewList.addAll(reservationResponse.reservationViewList);
                    });
                }
            });
        }
        SearchReservationResponse reservationResponse = new SearchReservationResponse();
        reservationResponse.total = total.get();
        reservationResponse.reservationViewList = reservationViewList;
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("user_total", userTotal);
        resultMap.put("restaurant_total", restaurantTotal);
        resultMap.put("reservation_result", reservationResponse);
        return Response.bean(resultMap);
    }
}
