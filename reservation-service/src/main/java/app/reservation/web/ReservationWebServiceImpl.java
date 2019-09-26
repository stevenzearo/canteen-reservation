package app.reservation.web;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.ReserveResponse;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.api.reservation.UpdateReservationRequest;
import app.reservation.service.ReservationService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class ReservationWebServiceImpl implements ReservationWebService {
    @Inject
    ReservationService service;

    @Override
    public ReserveResponse reserve(Long userId, ReserveRequest request) {
        return service.reserve(userId, request);
    }

    @Override
    public GetReservationResponse get(Long userId, String id) {
        return service.get(userId, id);
    }

    @Override
    public SearchReservationResponse search(Long userId, SearchReservationRequest request) {
        return service.search(userId, request);
    }

    @Override
    public void cancel(Long userId, String id) {
     service.cancel(userId, id);
    }
}
