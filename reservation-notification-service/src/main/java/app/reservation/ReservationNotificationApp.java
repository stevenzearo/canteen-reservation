package app.reservation;

import app.reservation.api.message.SendEmailReservationMessage;
import app.reservation.handler.ReservationMessageHandler;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author steve
 */
public class ReservationNotificationApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        load(new ReservationNotificationModule());
    }
}
