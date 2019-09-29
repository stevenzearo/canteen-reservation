package app.reservation.service;

import app.reservation.domain.EmailNotification;
import app.reservation.domain.EmailSendingStatus;
import app.reservation.domain.NotificationMeal;
import app.reservation.domain.NotificationRestaurant;
import app.reservation.service.notification.CreateEmailNotificationRequest;
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
        try (Transaction transaction = database.beginTransaction()) {
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

    public void cancel(String reservationId) {
        Query<EmailNotification> query = notificationRepository.select();
        if (!Strings.isBlank(reservationId))
            query.where("reservation_id = ?", reservationId);
        EmailNotification emailNotification = query.fetchOne().orElseThrow(() -> new NotFoundException(Strings.format("email notification not found, reservation id = {}", reservationId)));
        emailNotification.sendingStatus = EmailSendingStatus.CANCEL;
        notificationRepository.update(emailNotification);
    }
}
