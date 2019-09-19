package app.reservation.job;

import app.reservation.task.SendEmailTask;
import core.framework.async.Executor;
import core.framework.inject.Inject;
import core.framework.inject.Named;
import core.framework.scheduler.Job;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class ReservationJob implements Job {
    public String email;

    public String reservationId;

    public ZonedDateTime reserveDeadline;

    @Inject
    @Named("reservation-executor")
    private Executor executor;

    @Override
    public void execute() throws Exception {
        SendEmailTask sendEmailTask = new SendEmailTask();
        sendEmailTask.reservationId = reservationId;
        sendEmailTask.email = email;
        executor.submit("async", sendEmailTask, Duration.between(ZonedDateTime.now().plusSeconds(3), reserveDeadline));

//        executor.submit("async", sendEmailTask, Duration.between(ZonedDateTime.now().plusMinutes(10), reserveDeadline));
    }
}
