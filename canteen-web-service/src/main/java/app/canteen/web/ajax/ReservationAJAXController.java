package app.canteen.web.ajax;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.SearchReservationRequest;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author steve
 */
public class ReservationAJAXController {
    @Inject
    ReservationWebService service;

    public Response searchInFutureByUserId(Request request) {
        Map<String, String> paramMap = request.formParams();
        SearchReservationRequest reservationRequest = new SearchReservationRequest();
        reservationRequest.skip = Integer.valueOf(paramMap.get("skip"));
        reservationRequest.limit = Integer.valueOf(paramMap.get("limit"));
        reservationRequest.reservingTimeStart = JSON.fromJSON(ZonedDateTime.class, paramMap.get("now_time"));
        return Response.bean(service.search(Long.valueOf(paramMap.get("user_id")), reservationRequest));
    }
}
