package app.reservation.service.notification;

import app.reservation.domain.EmailSendingStatus;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class SearchEmailNotificationResponse {
    public Long total;

    public List<EmailNotificationView> notificationList;
}
