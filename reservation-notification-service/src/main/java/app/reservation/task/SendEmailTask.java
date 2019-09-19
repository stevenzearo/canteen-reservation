package app.reservation.task;

import app.reservation.api.ReservationWebService;
import app.reservation.api.reservation.ReservationStatus;
import app.reservation.api.reservation.ReservationView;
import core.framework.async.Task;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author steve
 */
public class SendEmailTask implements Task {
    private final Logger logger = LoggerFactory.getLogger(SendEmailTask.class);

    public String email;

    public String reservationId;

    @Inject
    ReservationWebService reservationWebService;

    private void sendEmail(String email, String reservationId) {
        logger.warn(Strings.format("According to reservationId = {}, sending email to {}...", reservationId, email));
    }

    @Override
    public void execute() throws Exception {
        if (!Strings.isBlank(email) && !Strings.isBlank(reservationId)) {
            ActionLogContext.put("executeSendEmailTask", email);
            ReservationView reservationView = reservationWebService.get(reservationId);
            if (reservationView.status == ReservationStatus.OK) sendEmail(email, reservationId);
        }
    }
}
