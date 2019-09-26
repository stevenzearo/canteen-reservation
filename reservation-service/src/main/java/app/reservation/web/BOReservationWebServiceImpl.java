package app.reservation.web;

import app.reservation.api.BOReservationWebService;
import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.BOGetReservationResponse;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.ReserveResponse;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.api.reservation.UpdateReservationRequest;
import app.reservation.service.BOReservationService;
import app.reservation.service.ReservationService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BOReservationWebServiceImpl implements BOReservationWebService {
    @Inject
    BOReservationService service;

    @Override
    public BOGetReservationResponse get(String id) {
        return service.get(id);
    }

    @Override
    public BOSearchReservationResponse search(BOSearchReservationRequest request) {
        return service.search(request);
    }

}
