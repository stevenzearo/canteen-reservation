package app.reservation.web;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReserveResponse;
import app.reservation.api.reservation.ReservingRequest;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.service.ReservationService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class ReservationWebServiceImpl implements ReservationWebService {
    @Inject
    ReservationService service;

    @Override
    public ReserveResponse reserve(Long userId, ReservingRequest request) {
        return service.reserve(userId, request);
    }

    @Override
    public GetReservationResponse get(Long userId, String id) {
        return service.get(userId, id);
    }

    @Override
    public SearchReservationResponse searchByTime(Long userId, SearchReservationRequest request) {
        return service.searchByTime(userId, request);
    }

    @Override
    public void cancel(Long userId, String id) {
        service.cancel(userId, id);
    }
}
