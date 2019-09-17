package app.reservation.task;

import core.framework.async.Task;
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

    private void sendEmail(String email, String reservationId) {
        logger.warn(Strings.format("Sending email to {}...", email));
    }

    @Override
    public void execute() throws Exception {
        if (!Strings.isBlank(email) && !Strings.isBlank(reservationId))
            sendEmail(email, reservationId);
    }
}
