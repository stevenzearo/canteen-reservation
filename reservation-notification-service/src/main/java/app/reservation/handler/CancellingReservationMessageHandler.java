package app.reservation.handler;

import app.reservation.api.kafka.CancellingReservationMessage;
import app.reservation.service.EmailNotificationService;
import app.reservation.service.notification.CancellingEmailNotificationRequest;
import app.user.api.BOUserWebService;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author steve
 */
public class CancellingReservationMessageHandler implements MessageHandler<CancellingReservationMessage> {
    private final Logger logger = LoggerFactory.getLogger(CancellingReservationMessageHandler.class);

    @Inject
    BOUserWebService userWebService;

    @Inject
    EmailNotificationService notificationService;

    @Override
    public void handle(String key, CancellingReservationMessage value) throws Exception {
        CancellingEmailNotificationRequest cancelRequest = new CancellingEmailNotificationRequest();
        cancelRequest.userEmail = userWebService.get(value.userId).email;
        cancelRequest.reservationId = value.reservationId;
        notificationService.cancel(cancelRequest);
        logger.warn(Strings.format("according to notification id = {}, sending email to {} cancelling reservation", cancelRequest.reservationId, cancelRequest.userEmail));
    }
}
