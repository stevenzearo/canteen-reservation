package app.reservation.service;

import app.reservation.domain.EmailNotification;
import app.reservation.domain.EmailSendingStatus;
import app.reservation.domain.NotificationMeal;
import app.reservation.domain.NotificationRestaurant;
import app.reservation.service.notification.CancellingEmailNotificationRequest;
import app.reservation.service.notification.CreateEmailNotificationRequest;
import app.reservation.service.notification.EmailNotificationView;
import app.reservation.service.notification.EmailSendingStatusView;
import app.reservation.service.notification.SearchEmailNotificationRequest;
import app.reservation.service.notification.SearchEmailNotificationResponse;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
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
    Repository<EmailNotification> notificationRepository;

    @Inject
    Repository<NotificationRestaurant> restaurantRepository;

    @Inject
    Repository<NotificationMeal> mealRepository;

    @Inject
    Database database;

    public void create(CreateEmailNotificationRequest request) {
        EmailNotification notification = new EmailNotification();
        notification.userEmail = request.userEmail;
        notification.reservationId = request.reservationId;
        logger.warn(Strings.format("save message to db, notification id = {}", notification.reservationId));
        notification.notifyingTime = request.notifyingTime;
        notification.sendingStatus = EmailSendingStatus.READY;
        try (Transaction transaction = database.beginTransaction()){
            OptionalLong notificationId = notificationRepository.insert(notification);
            if (notificationId.isPresent()) {
                NotificationRestaurant notificationRestaurant = new NotificationRestaurant();
                notificationRestaurant.restaurantId = request.restaurant.id;
                notificationRestaurant.notificationId = notificationId.getAsLong();
                notificationRestaurant.restaurantName = request.restaurant.name;
                notificationRestaurant.restaurantPhone = request.restaurant.phone;
                notificationRestaurant.restaurantAddress = request.restaurant.address;
                restaurantRepository.insert(notificationRestaurant);
                request.meals.forEach(meal -> {
                    NotificationMeal notificationMeal = new NotificationMeal();
                    notificationMeal.notificationId = notificationId.getAsLong();
                    notificationMeal.mealId = meal.id;
                    notificationMeal.mealName = meal.name;
                    notificationMeal.mealPrice = meal.price;
                    mealRepository.insert(notificationMeal);
                });
            }
            transaction.commit();
        }
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
                notificationRepository.update(notification);
            } else {
                throw new NotFoundException(Strings.format("Email Notification not found, reservation id = {} and user email = {} and notifying time = {}"
                    , request.reservationId, request.userEmail));
            }
        }

    }

    private SearchEmailNotificationResponse search(SearchEmailNotificationRequest request) {
        Query<EmailNotification> query = notificationRepository.select();
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
