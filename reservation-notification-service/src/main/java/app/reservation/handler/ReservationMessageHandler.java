package app.reservation.handler;

import app.reservation.api.message.SendEmailReservationMessage;
import app.reservation.job.SavingNotificationJob;
import app.reservation.service.reservation.CreateEmailNotificationRequest;
import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class ReservationMessageHandler implements MessageHandler<SendEmailReservationMessage> {
    @Inject
    UserWebService userWebService;

    @Inject
    SavingNotificationJob job;

    @Override
    public void handle(String key, SendEmailReservationMessage value) throws Exception {
        CreateEmailNotificationRequest createRequest = new CreateEmailNotificationRequest();
        createRequest.userEmail = userWebService.get(value.userId).email;
        createRequest.reservationId = value.reservationId;
        ZonedDateTime reservationDeadline = value.reservationDeadline;
        Duration between = Duration.between(ZonedDateTime.now().plusMinutes(10), reservationDeadline);
        createRequest.notifyingTime = ZonedDateTime.now().plus(between);
        job.createRequest = createRequest;
        // save data to db;
        job.execute();
    }
}
