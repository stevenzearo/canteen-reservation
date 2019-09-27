package app.reservation.api;

import app.reservation.api.reservation.BOGetReservationResponse;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author steve
 */
public interface BOReservationWebService {
    @GET
    @Path("/bo/reservation/:id")
    BOGetReservationResponse get(@PathParam("id") String id);

    @PUT
    @Path("/bo/reservation")
    BOSearchReservationResponse search(BOSearchReservationRequest request); // admin can search via reserving time range/user id/restaurant id/reservation status
}
