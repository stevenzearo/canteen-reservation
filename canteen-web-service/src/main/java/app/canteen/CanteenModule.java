package app.canteen;

import app.canteen.web.ajax.MealAJAXController;
import app.canteen.web.ajax.ReservationAJAXController;
import app.canteen.web.ajax.RestaurantAJAXController;
import app.canteen.web.ajax.UserAJAXController;
import app.canteen.web.ajax.meal.SearchMealAJAXRequest;
import app.canteen.web.ajax.reservation.CancellingReservationAJAXRequest;
import app.canteen.web.ajax.reservation.ReservingReservationAJAXRequest;
import app.canteen.web.ajax.reservation.SearchReservationAJAXRequest;
import app.canteen.web.ajax.restaurant.SearchRestaurantAJAXRequest;
import app.canteen.web.ajax.user.UserLoginAJAXRequest;
import app.canteen.web.ajax.user.UserRegistryAJAXRequest;
import app.canteen.web.interceptor.UserAuthorityInterceptor;
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
public class CanteenModule extends Module {
    @Override
    protected void initialize() {
        api().client(UserWebService.class, requiredProperty("app.user.serviceURL"));
        api().client(BOAdminWebService.class, requiredProperty("app.user.serviceURL"));
        api().client(RestaurantWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(MealWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(ReservationWebService.class, requiredProperty("app.reservation.serviceURL"));
        UserAJAXController userAJAX = bind(UserAJAXController.class);
        ReservationAJAXController reservationAJAX = bind(ReservationAJAXController.class);
        RestaurantAJAXController restaurantAJAX = bind(RestaurantAJAXController.class);
        MealAJAXController mealAJAX = bind(MealAJAXController.class);
        site().session().timeout(Duration.ofMinutes(30));
        http().intercept(bind(UserAuthorityInterceptor.class));

        http().bean(UserLoginAJAXRequest.class);
        http().bean(UserRegistryAJAXRequest.class);
        http().bean(ReservingReservationAJAXRequest.class);
        http().bean(CancellingReservationAJAXRequest.class);
        http().bean(SearchReservationAJAXRequest.class);
        http().bean(SearchMealAJAXRequest.class);
        http().bean(SearchRestaurantAJAXRequest.class);

        http().route(PUT, "/canteen/ajax/user/login", userAJAX::login);
        http().route(GET, "/canteen/ajax/user/logout", userAJAX::logout);
        http().route(POST, "/canteen/ajax/user/registry", userAJAX::register);
        http().route(POST, "/canteen/ajax/reservation", reservationAJAX::reserve);
        http().route(PUT, "/canteen/ajax/reservation/cancelling", reservationAJAX::cancel);
        http().route(PUT, "/canteen/ajax/reservation", reservationAJAX::search);
        http().route(GET, "/canteen/ajax/restaurant", restaurantAJAX::get);
        http().route(PUT, "/canteen/ajax/restaurant", restaurantAJAX::search);
        http().route(PUT, "/canteen/ajax/restaurant/:id/meal", mealAJAX::search);
    }
}
