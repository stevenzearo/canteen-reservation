package app.reservation.api;

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
    GetReservationResponse get(@PathParam("id") String id);

    @PUT
    @Path("/bo/reservation")
    SearchReservationResponse search(SearchReservationRequest request);

    @PUT
    @Path("/bo/reservation/:id")
    void update(@PathParam("id") String id, UpdateReservationRequest update);
}
