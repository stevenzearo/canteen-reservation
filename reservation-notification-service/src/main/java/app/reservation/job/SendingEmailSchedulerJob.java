package app.reservation.job;

import app.reservation.domain.EmailNotification;
import app.reservation.task.SendingEmailTask;
import core.framework.async.Executor;
import core.framework.async.Task;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.inject.Named;
import core.framework.scheduler.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author steve
 */
public class SendingEmailSchedulerJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(SendingEmailSchedulerJob.class);

    @Inject
    @Named("executor")
    Executor executor;

    @Inject
    SendingEmailTask task;

    @Override
    public void execute() throws Exception {
        logger.warn("executing sending email task");
        executor.submit("async", task);
    }
}
