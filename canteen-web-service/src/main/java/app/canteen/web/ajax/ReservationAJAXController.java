package app.canteen.web.ajax;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.SearchReservationRequest;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author steve
 */
public class ReservationAJAXController {
    @Inject
    ReservationWebService service;

    public Response searchInFutureByUserId(Request request) {
        Map<String, String> paramMap = request.queryParams();
        Long userId;
        try {
            userId = Long.valueOf(paramMap.get("user_id"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("incorrect user id");
        }
        SearchReservationRequest reservationRequest = request.bean(SearchReservationRequest.class);
        return Response.bean(service.searchByTime(userId, reservationRequest));
    }
}
