package app.reservation;

import app.reservation.domain.EmailNotification;
import app.reservation.job.SavingNotificationJob;
import app.reservation.job.SendingEmailSchedulerJob;
import app.reservation.service.EmailNotificationService;
import app.reservation.task.SendingEmailTask;
import app.user.api.UserWebService;
import core.framework.module.ExecutorConfig;
import core.framework.module.Module;
import core.framework.module.SchedulerConfig;

import java.time.Duration;

/**
 * @author steve
 */
public class ReservationNotificationModule extends Module {
    @Override
    protected void initialize() {
        db().repository(EmailNotification.class);
        api().client(UserWebService.class, requiredProperty("app.user.serviceURL"));
        bind(EmailNotificationService.class);
        bind(SavingNotificationJob.class);
        bind(SendingEmailTask.class);
        ExecutorConfig executorConfig = executor();
        executorConfig.add("executor", 5);
        SchedulerConfig schedulerConfig = schedule();
        SendingEmailSchedulerJob sendingEmailSchedulerJob = bind(SendingEmailSchedulerJob.class);
        schedulerConfig.fixedRate("scheduler", sendingEmailSchedulerJob, Duration.ofMinutes(1));
    }
}
