package app.canteen;

import app.canteen.web.ajax.AdminAJAXController;
import app.canteen.web.ajax.MealAJAXController;
import app.canteen.web.ajax.ReservationAJAXController;
import app.canteen.web.ajax.RestaurantAJAXController;
import app.canteen.web.ajax.UserAJAXController;
import app.canteen.web.ajax.admin.AdminLoginAJAXRequest;
import app.canteen.web.ajax.meal.CreateMealAJAXRequest;
import app.canteen.web.ajax.restaurant.CreateRestaurantAJAXRequest;
import app.canteen.web.ajax.restaurant.SearchRestaurantAJAXRequest;
import app.canteen.web.ajax.restaurant.UpdateAJAXRestaurantRequest;
import app.canteen.web.ajax.user.ActivateUserAJAXRequest;
import app.canteen.web.ajax.user.CreateUserAJAXRequest;
import app.canteen.web.ajax.user.RestUserPasswordAJAXRequest;
import app.canteen.web.ajax.user.SearchUserAJAXRequest;
import app.canteen.web.interceptor.AdminAuthorityInterceptor;
import app.reservation.api.BOReservationWebService;
import app.reservation.api.ReservationWebService;
import app.restaurant.api.BOMealWebService;
import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.MealWebService;
import app.restaurant.api.RestaurantWebService;
import app.user.api.BOAdminWebService;
import app.user.api.BOUserWebService;
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
        api().client(BOUserWebService.class, requiredProperty("app.user.serviceURL"));
        api().client(BOAdminWebService.class, requiredProperty("app.user.serviceURL"));
        api().client(BORestaurantWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(RestaurantWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(BOMealWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(MealWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(BOReservationWebService.class, requiredProperty("app.reservation.serviceURL"));
        api().client(ReservationWebService.class, requiredProperty("app.reservation.serviceURL"));
        AdminAJAXController adminAJAX = bind(AdminAJAXController.class);
        MealAJAXController mealAJAX = bind(MealAJAXController.class);
        UserAJAXController userAJAX = bind(UserAJAXController.class);
        RestaurantAJAXController restaurantAJAX = bind(RestaurantAJAXController.class);
        ReservationAJAXController reservationAJAX = bind(ReservationAJAXController.class);
        site().session().timeout(Duration.ofMinutes(30));
        http().intercept(bind(AdminAuthorityInterceptor.class));

        http().bean(AdminLoginAJAXRequest.class);
        http().bean(CreateMealAJAXRequest.class);
        http().bean(CreateRestaurantAJAXRequest.class);
        http().bean(UpdateAJAXRestaurantRequest.class);
        http().bean(CreateUserAJAXRequest.class);
        http().bean(RestUserPasswordAJAXRequest.class);
        http().bean(ActivateUserAJAXRequest.class);
        http().bean(SearchRestaurantAJAXRequest.class);
        http().bean(SearchUserAJAXRequest.class);

        http().route(PUT, "/canteen/ajax/admin/login", adminAJAX::login);
        http().route(GET, "/canteen/ajax/admin/logout", adminAJAX::logout);
        http().route(POST, "/canteen/ajax/restaurant", restaurantAJAX::create);
        http().route(GET, "/canteen/ajax/restaurant", restaurantAJAX::search);
        http().route(PUT, "/canteen/ajax/restaurant/deadline", restaurantAJAX::updateDeadline);
        http().route(POST, "/canteen/ajax/restaurant/:id/meal", mealAJAX::create);
        http().route(GET, "/canteen/ajax/user", userAJAX::search);
        http().route(PUT, "/canteen/ajax/user/status", userAJAX::activate);
        http().route(PUT, "/canteen/ajax/user/password", userAJAX::resetPassword);
        http().route(POST, "/canteen/ajax/user", userAJAX::createUser);
        http().route(PUT, "/canteen/ajax/reservation", reservationAJAX::search);
    }
}
