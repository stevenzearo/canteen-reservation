package app.reservation.handler;

import app.reservation.api.ReservationWebService;
import app.reservation.api.message.ReservationMessage;
import app.reservation.api.reservation.ReservationStatus;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.UpdateReservationRequest;
import app.reservation.job.ReservationJob;
import app.user.api.UserWebService;
import app.user.api.user.UserView;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;

/**
 * @author steve
 */
public class ReservationMessageHandler implements MessageHandler<ReservationMessage> {
    @Inject
    UserWebService userWebService;

    @Inject
    ReservationJob job;

    @Override
    public void handle(String key, ReservationMessage value) throws Exception {
        job.email = userWebService.get(value.userId).email;
        job.reservationId = value.reservationId;
        job.reserveDeadline = value.reservationDeadline;
        job.execute();
    }
}
