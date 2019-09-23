package app.reservation.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.ZonedDateTime;

/**
 * @author steve
 */

@Table(name = "email_notifications")
public class EmailNotification {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "user_email")
    public String userEmail;

    @NotNull
    @Column(name = "reservation_id")
    public String reservationId;

    @NotNull
    @Column(name = "notifying_time")
    public ZonedDateTime notifyingTime;

    @NotNull
    @Column(name = "sending_status")
    public EmailSendingStatus sendingStatus;
}
