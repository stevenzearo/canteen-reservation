package app.reservation;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.ReservationStatus;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.api.reservation.UpdateReservationRequest;
import app.reservation.domain.Reservation;
import app.reservation.domain.ReservationMeal;
import app.reservation.service.ReservationService;
import app.reservation.web.ReservationWebServiceImpl;
import core.framework.module.Module;

/**
 * @author steve
 */
public class ReservationModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Reservation.class);
        db().repository(ReservationMeal.class);
        http().bean(ReservationStatus.class);
        http().bean(ReservationView.class);
        http().bean(ReserveRequest.class);
        http().bean(SearchReservationRequest.class);
        http().bean(SearchReservationResponse.class);
        http().bean(UpdateReservationRequest.class);
        bind(ReservationService.class);
        api().service(ReservationWebService.class, bind(ReservationWebServiceImpl.class));
    }
}
