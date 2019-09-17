package app.reservation.job;

import app.reservation.task.SendEmailTask;
import core.framework.async.Executor;
import core.framework.inject.Inject;
import core.framework.inject.Named;
import core.framework.scheduler.Job;

import java.time.Duration;

/**
 * @author steve
 */
public class ReservationJob implements Job {
    @Inject
    @Named("reservation-executor")
    private Executor executor;

    public String email;

    public String reservationId;
    @Override
    public void execute() throws Exception {
        SendEmailTask sendEmailTask = new SendEmailTask();
        sendEmailTask.reservationId = reservationId;
        sendEmailTask.email = email;
        executor.submit("async", sendEmailTask, Duration.ofSeconds(10 * 60));
    }
}
