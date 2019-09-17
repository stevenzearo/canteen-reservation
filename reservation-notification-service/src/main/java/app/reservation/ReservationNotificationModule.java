package app.reservation;

import app.reservation.job.ReservationJob;
import app.user.api.UserWebService;
import core.framework.module.ExecutorConfig;
import core.framework.module.Module;

/**
 * @author steve
 */
public class ReservationNotificationModule extends Module {
    @Override
    protected void initialize() {
        ExecutorConfig executorConfig = executor();
        executorConfig.add("reservation-executor", 5);
        bind(ReservationJob.class);
        api().client(UserWebService.class, requiredProperty("app.user.serviceURL"));

    }
}
