package app.reservation.job;

import app.reservation.domain.EmailNotification;
import app.reservation.domain.EmailSendingStatus;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.scheduler.Job;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class SendingEmailSchedulerJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(SendingEmailSchedulerJob.class);

    @Inject
    Repository<EmailNotification> repository;

    @Override
    public void execute() throws Exception {
        logger.warn("executing sending email task");
        Query<EmailNotification> query = repository.select();
        int skip = 0;
        int limit = 10;
        int count;
        do {
            query.where("sending_status = ?", EmailSendingStatus.READY);
            query.where("notifying_time <= ?", ZonedDateTime.now());
            query.orderBy("notifying_time ASC");
            query.skip(skip);
            query.limit(limit);
            count = query.count();
            if (count > 0) {
                List<EmailNotification> emailNotificationList = query.fetch();
                emailNotificationList.forEach(notification -> {
                    sendEmail(notification.userEmail, notification.reservationId);
                    changeNotificationStatus(notification);
                });
            }
        } while (count < limit);
    }

    private void sendEmail(String email, String reservationId) {
        logger.warn(Strings.format("according to reservation id = {}, sending email to {}", reservationId, email));
    }

    private void changeNotificationStatus(EmailNotification notification) {
        notification.sendingStatus = EmailSendingStatus.SENT;
        repository.update(notification);
    }
}
