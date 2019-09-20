package app.reservation.service.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class CreateEmailNotificationResponse {
    public Long id;

    public String userEmail;

    public String reservationId;

    public ZonedDateTime notifyingTime;

    public EmailNotificationStatusView sendingStatus;
}
