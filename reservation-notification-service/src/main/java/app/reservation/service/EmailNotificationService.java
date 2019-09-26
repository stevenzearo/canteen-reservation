package app.reservation.service;

import app.reservation.domain.EmailNotification;
import app.reservation.domain.EmailSendingStatus;
import app.reservation.service.notification.CancellingEmailNotificationRequest;
import app.reservation.service.notification.CreateEmailNotificationRequest;
import app.reservation.service.notification.CreateEmailNotificationResponse;
import app.reservation.service.notification.EmailNotificationView;
import app.reservation.service.notification.EmailSendingStatusView;
import app.reservation.service.notification.SearchEmailNotificationRequest;
import app.reservation.service.notification.SearchEmailNotificationResponse;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.OptionalLong;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class EmailNotificationService {
    private final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    @Inject
    Repository<EmailNotification> repository;

    public CreateEmailNotificationResponse create(CreateEmailNotificationRequest request) {
        EmailNotification notification = new EmailNotification();
        notification.userEmail = request.userEmail;
        notification.reservationId = request.reservationId;
        logger.warn(Strings.format("save message to db, notification id = {}", notification.reservationId));
        notification.notifyingTime = request.notifyingTime;
        notification.sendingStatus = EmailSendingStatus.READY;
        OptionalLong insert = repository.insert(notification);
        if (insert.isPresent()) notification.id = insert.getAsLong();
        CreateEmailNotificationResponse response = new CreateEmailNotificationResponse();
        response.id = notification.id;
        response.userEmail = notification.userEmail;
        response.reservationId = notification.reservationId;
        response.notifyingTime = notification.notifyingTime;
        response.sendingStatus = EmailSendingStatusView.valueOf(notification.sendingStatus.name());
        return response;
    }

    public void cancel(CancellingEmailNotificationRequest request) {
        if (!Strings.isBlank(request.reservationId) && !Strings.isBlank(request.userEmail)) {
            SearchEmailNotificationRequest searchRequest = new SearchEmailNotificationRequest();
            searchRequest.reservationId = request.reservationId;
            searchRequest.userEmail = request.userEmail;
            SearchEmailNotificationResponse search = search(searchRequest);
            if (search.total == 1) {
                EmailNotification notification = new EmailNotification();
                notification.id = search.notificationList.get(0).id;
                notification.reservationId = search.notificationList.get(0).reservationId;
                notification.userEmail = search.notificationList.get(0).userEmail;
                notification.userEmail = search.notificationList.get(0).userEmail;
                notification.notifyingTime = search.notificationList.get(0).notifyingTime;
                notification.sendingStatus = EmailSendingStatus.CANCEL;
                repository.update(notification);
            } else {
                throw new NotFoundException(Strings.format("Email Notification not found, reservation id = {} and user email = {} and notifying time = {}"
                    , request.reservationId, request.userEmail));
            }
        }

    }

    private SearchEmailNotificationResponse search(SearchEmailNotificationRequest request) {
        Query<EmailNotification> query = repository.select();
        if (!Strings.isBlank(request.userEmail))
            query.where("user_email = ?", request.userEmail);
        if (!Strings.isBlank(request.reservationId))
            query.where("reservation_id = ?", request.reservationId);
        if (request.notifyingTime != null)
            query.where("notifying_time = ?", request.notifyingTime);
        SearchEmailNotificationResponse response = new SearchEmailNotificationResponse();
        response.total = (long) query.count();
        response.notificationList = query.fetch().stream().map(this::view).collect(Collectors.toList());
        return response;
    }

    private EmailNotificationView view(EmailNotification notification) {
        EmailNotificationView notificationView = new EmailNotificationView();
        notificationView.id = notification.id;
        notificationView.userEmail = notification.userEmail;
        notificationView.reservationId = notification.reservationId;
        notificationView.notifyingTime = notification.notifyingTime;
        notificationView.sendingStatus = EmailSendingStatusView.valueOf(notification.sendingStatus.name());
        return notificationView;
    }
}
