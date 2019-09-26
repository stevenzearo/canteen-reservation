package app.canteen.web.ajax;

import app.canteen.service.BOCombineSearchReservationService;
import app.canteen.service.reservation.BOCombineSearchReservationRequest;
import app.canteen.service.reservation.BOCombineSearchReservationResponse;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class ReservationBOAJAXController {
    @Inject
    BOCombineSearchReservationService boCombineSearchReservationService;

    // search notification via username/restaurant name/booking date
    public Response search(Request request) {
        BOCombineSearchReservationRequest combineRequest = request.bean(BOCombineSearchReservationRequest.class);
        BOCombineSearchReservationResponse searchResponse = new BOCombineSearchReservationResponse();
        if (Strings.isBlank(combineRequest.userName) && Strings.isBlank(combineRequest.restaurantName)) {
            searchResponse = boCombineSearchReservationService.search(combineRequest);
        } else if (!Strings.isBlank(combineRequest.userName) && !Strings.isBlank(combineRequest.userName)) {
            searchResponse = boCombineSearchReservationService.searchByUserNameAndRestaurantName(combineRequest);
        } else if (!Strings.isBlank(combineRequest.userName)) {
            searchResponse = boCombineSearchReservationService.searchByUserName(combineRequest);
        } else if (!Strings.isBlank(combineRequest.restaurantName)) {
            searchResponse = boCombineSearchReservationService.searchByRestaurantName(combineRequest);
        }
        return Response.bean(searchResponse);
    }
}
