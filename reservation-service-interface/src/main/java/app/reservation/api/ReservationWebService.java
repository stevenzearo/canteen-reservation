package app.reservation.api;

import app.reservation.api.reservation.ReserveRequest;
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
public interface ReservationWebService {
    @POST
    @Path("/reservation")
    ReservationView reserve(ReserveRequest request);

    @GET
    @Path("/reservation/:id")
    ReservationView get(@PathParam("id") String id);

    @PUT
    @Path("/reservation")
    SearchReservationResponse searchListByConditions(SearchReservationRequest request);

    @POST
    @Path("/reservation/:id")
    void update(@PathParam("id") String id, UpdateReservationRequest update);
}
