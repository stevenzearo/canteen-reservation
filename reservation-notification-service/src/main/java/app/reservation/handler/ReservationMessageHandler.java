package app.reservation.handler;

import app.reservation.api.message.MessageStatus;
import app.reservation.api.message.SendEmailReservationMessage;
import app.reservation.service.EmailNotificationService;
import app.reservation.service.notification.CancelEmailNotificationRequest;
import app.reservation.service.notification.CreateEmailNotificationRequest;
import app.user.api.UserWebService;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class ReservationMessageHandler implements MessageHandler<SendEmailReservationMessage> {
    @Inject
    UserWebService userWebService;

    @Inject
    EmailNotificationService notificationService;

    private final Logger logger = LoggerFactory.getLogger(ReservationMessageHandler.class);

    @Override
    public void handle(String key, SendEmailReservationMessage value) throws Exception {
        logger.warn(Strings.format("handling SendEmailReservationMessage key = {}", key));
        if (value.status == MessageStatus.CREATE) {
            CreateEmailNotificationRequest createRequest = new CreateEmailNotificationRequest();
            createRequest.userEmail = userWebService.get(value.userId).email;
            createRequest.reservationId = value.reservationId;
            ZonedDateTime reservationDeadline = value.reservationDeadline;
            Duration between = Duration.between(ZonedDateTime.now().plusMinutes(10), reservationDeadline);
            createRequest.notifyingTime = ZonedDateTime.now().plus(between);
            logger.warn(Strings.format("ReservationMessageHandler handle notification id = {}", createRequest.reservationId));
            // save data to db;
            notificationService.create(createRequest);
        } else if (value.status == MessageStatus.CANCEL) {
            CancelEmailNotificationRequest cancelRequest = new CancelEmailNotificationRequest();
            cancelRequest.userEmail = userWebService.get(value.userId).email;
            cancelRequest.reservationId = value.reservationId;
            notificationService.cancel(cancelRequest);
            logger.warn(Strings.format("according to notification id = {}, sending email to {} cancelling reserving", cancelRequest.reservationId, cancelRequest.userEmail));
        }

    }
}
