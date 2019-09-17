package app.reservation.web;

import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.api.reservation.UpdateReservationRequest;
import app.reservation.service.ReservationService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class ReservationWebServiceImpl implements app.reservation.api.ReservationWebService {
    @Inject
    ReservationService service;
    @Override
    public ReservationView reserve(ReserveRequest request) {
        return service.reserve(request);
    }

    @Override
    public SearchReservationResponse searchListByConditions(SearchReservationRequest request) {
        return service.searchListByConditions(request);
    }

    @Override
    public void update(String id, UpdateReservationRequest update) {
        service.update(id, update);
    }
}
