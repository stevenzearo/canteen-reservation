package app.reservation.api;

import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservingRequest;
import app.reservation.api.reservation.ReservingResponse;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author steve
 */
public interface ReservationWebService {
    @POST
    @ResponseStatus(HTTPStatus.CREATED)
    @Path("/user/:userId/reservation")
    ReservingResponse reserve(@PathParam("userId") Long userId, ReservingRequest request); // return for user confirm

    @GET
    @Path("/user/:userId/reservation/:id")
    GetReservationResponse get(@PathParam("userId") Long userId, @PathParam("id") String id);

    @PUT
    @Path("/user/:userId/reservation")
    SearchReservationResponse search(@PathParam("userId") Long userId, SearchReservationRequest request); // user can only search via reserving time range

    @PUT
    @Path("/user/:userId/reservation/:id")
    void cancel(@PathParam("userId") Long userId, @PathParam("id") String id); // about update, user can only cancel reservation
}
