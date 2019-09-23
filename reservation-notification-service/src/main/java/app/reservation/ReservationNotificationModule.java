package app.reservation;

import app.reservation.api.message.SendEmailReservationMessage;
import app.reservation.domain.EmailNotification;
import app.reservation.handler.ReservationMessageHandler;
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
        bind(SendingEmailTask.class);

        kafka().groupId("group1");
        kafka().subscribe("notification", SendEmailReservationMessage.class, bind(ReservationMessageHandler.class));

        ExecutorConfig executorConfig = executor();
        executorConfig.add("executor", 5);
        SchedulerConfig schedulerConfig = schedule();
        SendingEmailSchedulerJob sendingEmailSchedulerJob = bind(SendingEmailSchedulerJob.class);
        schedulerConfig.fixedRate("scheduler", sendingEmailSchedulerJob, Duration.ofMinutes(1));
    }
}
