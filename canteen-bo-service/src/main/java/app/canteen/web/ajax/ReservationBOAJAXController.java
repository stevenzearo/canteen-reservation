package app.canteen.web.ajax;

import app.canteen.service.reservation.BOCombineSearchReservationRequest;
import app.canteen.service.reservation.BOCombineSearchReservationResponse;
import app.canteen.service.BOCombineSearchReservationService;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author steve
 */
public class ReservationBOAJAXController {
    @Inject
    BOCombineSearchReservationService boCombineSearchReservationService;

    // search notification via username/restaurant name/booking date
    public Response search(Request request) {
        // todo
        Map<String, String> paramMap = request.formParams();
        String userName = paramMap.get("user_name");
        String restaurantName = paramMap.get("restaurant_name");
        BOCombineSearchReservationResponse boSearchResponse = new BOCombineSearchReservationResponse();
        BOCombineSearchReservationRequest boSearchRequest = new BOCombineSearchReservationRequest();
        if (!Strings.isBlank(paramMap.get("reserving_date")))
            // todo
            boSearchRequest.reservingDate = JSON.fromJSON(ZonedDateTime.class, paramMap.get("reserving_date"));
        if (Strings.isBlank(userName) && Strings.isBlank(restaurantName)) {
            boSearchRequest.reservationSkip = Integer.valueOf(paramMap.get("reservation_skip"));
            boSearchRequest.reservationLimit = Integer.valueOf(paramMap.get("reservation_limit"));
            boSearchResponse = boCombineSearchReservationService.search(boSearchRequest);
        } else if (!Strings.isBlank(userName) && !Strings.isBlank(restaurantName)) {
            boSearchRequest.userSkip = Integer.valueOf(paramMap.get("user_skip"));
            boSearchRequest.userLimit = Integer.valueOf(paramMap.get("user_limit"));
            boSearchRequest.userName = userName;
            boSearchRequest.reservationSkip = Integer.valueOf(paramMap.get("restaurant_skip"));
            boSearchRequest.reservationLimit = Integer.valueOf(paramMap.get("restaurant_limit"));
            boSearchRequest.restaurantName = restaurantName;
            boSearchResponse = boCombineSearchReservationService.searchByUserNameAndRestaurantName(boSearchRequest);

        } else if (!Strings.isBlank(userName)) {
            boSearchRequest.userSkip = Integer.valueOf(paramMap.get("user_skip"));
            boSearchRequest.userLimit = Integer.valueOf(paramMap.get("user_limit"));
            boSearchRequest.userName = userName;
            boSearchResponse = boCombineSearchReservationService.searchByUserName(boSearchRequest);
        } else if (!Strings.isBlank(restaurantName)) {
            boSearchRequest.reservationSkip = Integer.valueOf(paramMap.get("restaurant_skip"));
            boSearchRequest.reservationLimit = Integer.valueOf(paramMap.get("restaurant_limit"));
            boSearchRequest.restaurantName = restaurantName;
            boSearchResponse = boCombineSearchReservationService.searchByRestaurantName(boSearchRequest);
        }

        return Response.bean(boSearchResponse);
    }

}
