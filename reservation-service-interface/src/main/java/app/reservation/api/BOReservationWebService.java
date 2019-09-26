package app.reservation.api;

import app.reservation.api.reservation.BOGetReservationResponse;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.api.reservation.UpdateReservationRequest;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
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
    BOSearchReservationResponse search(BOSearchReservationRequest request);
}
