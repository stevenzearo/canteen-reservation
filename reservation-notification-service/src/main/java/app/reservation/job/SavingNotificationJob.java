package app.reservation.job;

import app.reservation.service.EmailNotificationService;
import app.reservation.service.reservation.CreateEmailNotificationRequest;
import core.framework.inject.Inject;
import core.framework.scheduler.Job;

/**
 * @author steve
 */
public class SavingNotificationJob implements Job {
    public CreateEmailNotificationRequest createRequest;

    @Inject
    EmailNotificationService service;

    @Override
    public void execute() throws Exception {
        service.create(createRequest);
    }
}
