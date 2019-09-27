package app.canteen.web.ajax;

import app.canteen.service.CombineSearchReservationService;
import app.canteen.service.reservation.CombineSearchReservationRequest;
import app.canteen.service.reservation.CombineSearchReservationResponse;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class ReservationAJAXController {
    @Inject
    CombineSearchReservationService combineSearchReservationService;

    // search notification via username/restaurant name/booking date
    public Response search(Request request) {
        CombineSearchReservationRequest combineRequest = request.bean(CombineSearchReservationRequest.class);
        CombineSearchReservationResponse searchResponse = new CombineSearchReservationResponse();
        if (Strings.isBlank(combineRequest.userName) && Strings.isBlank(combineRequest.restaurantName)) {
            searchResponse = combineSearchReservationService.search(combineRequest);
        } else if (!Strings.isBlank(combineRequest.userName) && !Strings.isBlank(combineRequest.restaurantName)) {
            searchResponse = combineSearchReservationService.searchByUserNameAndRestaurantName(combineRequest);
        } else if (!Strings.isBlank(combineRequest.userName)) {
            searchResponse = combineSearchReservationService.searchByUserName(combineRequest);
        } else if (!Strings.isBlank(combineRequest.restaurantName)) {
            searchResponse = combineSearchReservationService.searchByRestaurantName(combineRequest);
        }
        return Response.bean(searchResponse);
    }
}
