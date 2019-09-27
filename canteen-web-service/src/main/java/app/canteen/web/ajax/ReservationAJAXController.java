package app.canteen.web.ajax;

import app.canteen.web.ajax.reservation.SearchReservationAJAXRequest;
import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.SearchReservationRequest;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Map;

/**
 * @author steve
 */
public class ReservationAJAXController {
    @Inject
    ReservationWebService service;

    public Response searchInFutureByUserId(Request request) {
        Map<String, String> paramMap = request.queryParams();
        Long userId = Long.valueOf(paramMap.get("user_id"));
        SearchReservationAJAXRequest controllerRequest = request.bean(SearchReservationAJAXRequest.class);
        SearchReservationRequest reservationRequest = new SearchReservationRequest();
        reservationRequest.skip = controllerRequest.skip;
        reservationRequest.limit = controllerRequest.limit;
        reservationRequest.reservingTimeStart = controllerRequest.reservingTimeStart;
        reservationRequest.reservingTimeEnd = controllerRequest.reservingTimeEnd;
        return Response.bean(service.searchByTime(userId, reservationRequest));
    }
}
