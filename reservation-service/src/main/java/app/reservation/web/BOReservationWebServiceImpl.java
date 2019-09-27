package app.reservation.web;

import app.reservation.api.BOReservationWebService;
import app.reservation.api.reservation.BOGetReservationResponse;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import app.reservation.service.BOReservationService;
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
