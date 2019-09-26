package app.reservation.handler;

import app.reservation.api.kafka.SendingEmailReservationMessage;
import app.reservation.service.EmailNotificationService;
import app.reservation.service.notification.CreateEmailNotificationRequest;
import app.restaurant.api.BOMealWebService;
import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.meal.BOGetMealResponse;
import app.restaurant.api.restaurant.BOGetRestaurantResponse;
import app.user.api.BOUserWebService;
import app.user.api.user.CreateUserRequest;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;
import core.framework.util.Lists;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class SendingReservationEmailHandler implements MessageHandler<SendingEmailReservationMessage> {
    private final Logger logger = LoggerFactory.getLogger(SendingReservationEmailHandler.class);
    @Inject
    BOUserWebService userWebService;

    @Inject
    BORestaurantWebService restaurantWebService;

    @Inject
    BOMealWebService mealWebService;

    @Inject
    EmailNotificationService notificationService;

    @Override
    public void handle(String key, SendingEmailReservationMessage value) throws Exception {
        logger.warn(Strings.format("handling SendReservationEmailMessage key = {}", key));
        CreateEmailNotificationRequest createRequest = new CreateEmailNotificationRequest();
        createRequest.userEmail = userWebService.get(value.userId).email;
        createRequest.reservationId = value.reservationId;
        BOGetRestaurantResponse restaurant = restaurantWebService.get(value.reservationId);
        createRequest.restaurant = new CreateEmailNotificationRequest.Restaurant();
        createRequest.restaurant.id = restaurant.id;
        createRequest.restaurant.name = restaurant.name;
        createRequest.restaurant.phone = restaurant.phone;
        createRequest.restaurant.address = restaurant.address;
        createRequest.meals = Lists.newArrayList();
        value.mealIdList.forEach(mealId -> {
            BOGetMealResponse meal = mealWebService.get(value.restaurantId, mealId);
            CreateEmailNotificationRequest.Meal notificationMeal = new CreateEmailNotificationRequest.Meal();
            notificationMeal.id = meal.id;
            notificationMeal.name = meal.name;
            notificationMeal.price = meal.price;
            createRequest.meals.add(notificationMeal);
        });
        ZonedDateTime reservationDeadline = value.reservationDeadline;
        Duration between = Duration.between(ZonedDateTime.now().plusMinutes(10), reservationDeadline);
        createRequest.notifyingTime = ZonedDateTime.now().plus(between);
        logger.warn(Strings.format("SendingReservationEmailHandler handle notification id = {}", createRequest.reservationId));
        // save data to db;
        notificationService.create(createRequest);
    }
}
