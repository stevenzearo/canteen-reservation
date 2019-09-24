package app.canteen;

import app.canteen.web.ajax.MealAJAXController;
import app.canteen.web.ajax.ReservationAJAXController;
import app.canteen.web.ajax.RestaurantAJAXController;
import app.canteen.web.controller.ReservationController;
import app.canteen.web.controller.UserController;
import app.reservation.api.ReservationWebService;
import app.restaurant.api.MealWebService;
import app.restaurant.api.RestaurantWebService;
import app.user.api.BOAdminWebService;
import app.user.api.UserWebService;
import core.framework.module.Module;

import java.time.Duration;

import static core.framework.http.HTTPMethod.GET;
import static core.framework.http.HTTPMethod.POST;
import static core.framework.http.HTTPMethod.PUT;

/**
 * @author steve
 */
public class CanteenWebModule extends Module {
    @Override
    protected void initialize() {
        api().client(UserWebService.class, requiredProperty("app.user.serviceURL"));
        api().client(BOAdminWebService.class, requiredProperty("app.user.serviceURL"));
        api().client(RestaurantWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(MealWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(ReservationWebService.class, requiredProperty("app.reservation.serviceURL"));
        UserController user = bind(UserController.class);
        ReservationController reservation = bind(ReservationController.class);
        RestaurantAJAXController restaurantAJAX = bind(RestaurantAJAXController.class);
        MealAJAXController mealAJAX = bind(MealAJAXController.class);
        ReservationAJAXController reservationAJAX = bind(ReservationAJAXController.class);
        site().session().timeout(Duration.ofMinutes(30));
        http().route(PUT, "/canteen/login", user::login);
        http().route(GET, "/canteen/logout", user::logout);
        http().route(POST, "/canteen/registry", user::register);
        http().route(POST, "/canteen/reservation", reservation::reserve);
        http().route(PUT, "/canteen/reservation/canceling", reservation::cancel);
        http().route(PUT, "/canteen/ajax/reservation", reservationAJAX::searchInFutureByUserId);
        http().route(PUT, "/canteen/ajax/meal", mealAJAX::searchValidByRestaurantId);
        http().route(PUT, "/canteen/ajax/restaurant", restaurantAJAX::searchAvailableByDate);
    }
}
