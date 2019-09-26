package app.canteen;

import app.canteen.service.reservation.BOCombineSearchReservationResponse;
import app.canteen.service.BOCombineSearchReservationService;
import app.canteen.web.ajax.ReservationBOAJAXController;
import app.canteen.web.ajax.RestaurantBOAJAXController;
import app.canteen.web.ajax.UserBOAJAXController;
import app.canteen.web.controller.AdminBOController;
import app.canteen.web.controller.MealBOController;
import app.canteen.web.controller.RestaurantBOController;
import app.canteen.web.controller.UserBOController;
import app.canteen.web.interceptor.CanteenBOServiceInterceptor;
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
public class CanteenBoModule extends Module {
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
        bind(BOCombineSearchReservationService.class);
        AdminBOController admin = bind(AdminBOController.class);
        UserBOController user = bind(UserBOController.class);
        RestaurantBOController restaurant = bind(RestaurantBOController.class);
        MealBOController meal = bind(MealBOController.class);
        UserBOAJAXController userJAX = bind(UserBOAJAXController.class);
        RestaurantBOAJAXController restaurantAJAX = bind(RestaurantBOAJAXController.class);
        ReservationBOAJAXController reservationAJAX = bind(ReservationBOAJAXController.class);
        site().session().timeout(Duration.ofMinutes(30));
        http().intercept(bind(CanteenBOServiceInterceptor.class));
        http().route(PUT, "/canteen/bo/admin/login", admin::login);
        http().route(GET, "/canteen/bo/admin/logout", admin::logout);
        http().route(POST, "/canteen/bo/meal", meal::create);
        http().route(POST, "/canteen/bo/restaurant", restaurant::create);
        http().route(PUT, "/canteen/bo/restaurant/deadline", restaurant::updateDeadline);
        http().route(POST, "/canteen/bo/user", user::createUser);
        http().route(PUT, "/canteen/bo/user/password", user::resetPassword);
        http().route(PUT, "/canteen/bo/user/status", user::activate);
        http().route(PUT, "/canteen/bo/ajax/reservation", reservationAJAX::search);
        http().route(GET, "/canteen/bo/ajax/restaurant", restaurantAJAX::searchByPage);
        http().route(GET, "/canteen/bo/ajax/user", userJAX::searchByPage);
        http().bean(BOCombineSearchReservationResponse.class);
    }
}
