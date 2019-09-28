package app.canteen.web.ajax;

import app.canteen.web.ajax.reservation.SearchReservationAJAXRequest;
import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.SearchReservationRequest;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class ReservationAJAXController {
    @Inject
    ReservationWebService service;

    public Response search(Request request) {
        SearchReservationAJAXRequest controllerRequest = request.bean(SearchReservationAJAXRequest.class);
        SearchReservationRequest reservationRequest = new SearchReservationRequest();
        reservationRequest.skip = controllerRequest.skip;
        reservationRequest.limit = controllerRequest.limit;
        reservationRequest.reservingTimeStart = controllerRequest.reservingTimeStart;
        reservationRequest.reservingTimeEnd = controllerRequest.reservingTimeEnd;
        return Response.bean(service.search(controllerRequest.userId, reservationRequest));
    }
}
