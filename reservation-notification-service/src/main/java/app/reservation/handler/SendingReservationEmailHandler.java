package app.reservation.handler;

import app.reservation.api.kafka.SendingEmailReservationMessage;
import app.reservation.service.EmailNotificationService;
import app.reservation.service.notification.CreateEmailNotificationRequest;
import app.reservation.service.notification.CreateEmailNotificationResponse;
import app.user.api.UserWebService;
import app.user.api.user.CreateUserResponse;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.kafka.MessageHandler;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class SendingReservationEmailHandler implements MessageHandler<SendingEmailReservationMessage> {
    private final Logger logger = LoggerFactory.getLogger(SendingReservationEmailHandler.class);
    @Inject
    UserWebService userWebService;
    @Inject
    EmailNotificationService notificationService;

    @Override
    public void handle(String key, SendingEmailReservationMessage value) throws Exception {
        logger.warn(Strings.format("handling SendReservationEmailMessage key = {}", key));
        CreateEmailNotificationRequest createRequest = new CreateEmailNotificationRequest();
        createRequest.userEmail = userWebService.get(value.userId).email;
        createRequest.reservationId = value.reservationId;
        ZonedDateTime reservationDeadline = value.reservationDeadline;
        Duration between = Duration.between(ZonedDateTime.now().plusMinutes(10), reservationDeadline);
        createRequest.notifyingTime = ZonedDateTime.now().plus(between);
        logger.warn(Strings.format("SendingReservationEmailHandler handle notification id = {}", createRequest.reservationId));
        // save data to db;
        CreateEmailNotificationResponse createEmailNotificationResponse = notificationService.create(createRequest);
        logger.warn(Strings.format("created email notification: {}", JSON.toJSON(createEmailNotificationResponse)));
    }
}
