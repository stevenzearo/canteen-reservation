package app.reservation;

import app.reservation.api.kafka.CancellingReservationMessage;
import app.reservation.api.kafka.SendingEmailReservationMessage;
import app.reservation.domain.EmailNotification;
import app.reservation.handler.CancellingReservationMessageHandler;
import app.reservation.handler.SendingReservationEmailHandler;
import app.reservation.job.SendingEmailSchedulerJob;
import app.reservation.service.EmailNotificationService;
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

        kafka().groupId("group1");
        kafka().subscribe("sending-email-reservation", SendingEmailReservationMessage.class, bind(SendingReservationEmailHandler.class));
        kafka().subscribe("cancelling-reservation", CancellingReservationMessage.class, bind(CancellingReservationMessageHandler.class));

        ExecutorConfig executorConfig = executor();
        executorConfig.add("executor", 5);
        SchedulerConfig schedulerConfig = schedule();
        SendingEmailSchedulerJob sendingEmailSchedulerJob = bind(SendingEmailSchedulerJob.class);
        schedulerConfig.fixedRate("scheduler", sendingEmailSchedulerJob, Duration.ofMinutes(1));
    }
}
