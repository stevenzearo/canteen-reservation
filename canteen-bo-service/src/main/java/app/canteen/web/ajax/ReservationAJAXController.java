package app.canteen.web.ajax;

import app.canteen.web.ajax.reservation.SearchReservationAJAXRequest;
import app.reservation.api.BOReservationWebService;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class ReservationAJAXController {
    @Inject
    BOReservationWebService service;

    // search notification via username/restaurant name/booking date
    public Response search(Request request) {
        SearchReservationAJAXRequest controllerRequest = request.bean(SearchReservationAJAXRequest.class);
        BOSearchReservationRequest searchRequest = new BOSearchReservationRequest();
        searchRequest.skip = controllerRequest.skip;
        searchRequest.limit = controllerRequest.limit;
        searchRequest.userName = controllerRequest.userName;
        searchRequest.restaurantName = controllerRequest.restaurantName;
        searchRequest.reservingTimeStart = controllerRequest.reservingTimeStart;
        searchRequest.reservingTimeEnd = controllerRequest.reservingTimeEnd;
        BOSearchReservationResponse searchResponse = service.search(searchRequest);
        return Response.bean(searchResponse);
    }
}
