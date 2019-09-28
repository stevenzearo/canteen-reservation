package app.canteen;

import app.canteen.service.CombineSearchReservationService;
import app.canteen.service.reservation.CombineSearchReservationRequest;
import app.canteen.service.reservation.CombineSearchReservationResponse;
import app.canteen.web.ajax.ReservationAJAXController;
import app.canteen.web.ajax.RestaurantAJAXController;
import app.canteen.web.ajax.UserAJAXController;
import app.canteen.web.ajax.restaurant.SearchRestaurantAJAXRequest;
import app.canteen.web.ajax.user.SearchUserAJAXRequest;
import app.canteen.web.controller.AdminController;
import app.canteen.web.controller.MealController;
import app.canteen.web.controller.RestaurantController;
import app.canteen.web.controller.UserController;
import app.canteen.web.controller.admin.AdminLoginRequest;
import app.canteen.web.controller.meal.CreateMealRequest;
import app.canteen.web.controller.restaurant.CreateRestaurantRequest;
import app.canteen.web.controller.restaurant.UpdateRestaurantRequest;
import app.canteen.web.controller.user.ActivateUserRequest;
import app.canteen.web.controller.user.CreateUserRequest;
import app.canteen.web.controller.user.RestUserPasswordRequest;
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
        bind(CombineSearchReservationService.class);
        AdminController admin = bind(AdminController.class);
        UserController user = bind(UserController.class);
        RestaurantController restaurant = bind(RestaurantController.class);
        MealController meal = bind(MealController.class);
        UserAJAXController userAJAX = bind(UserAJAXController.class);
        RestaurantAJAXController restaurantAJAX = bind(RestaurantAJAXController.class);
        ReservationAJAXController reservationAJAX = bind(ReservationAJAXController.class);
        site().session().timeout(Duration.ofMinutes(30));
        http().intercept(bind(AdminAuthorityInterceptor.class));

        http().bean(AdminLoginRequest.class);
        http().bean(CreateMealRequest.class);
        http().bean(CreateRestaurantRequest.class);
        http().bean(UpdateRestaurantRequest.class);
        http().bean(CreateUserRequest.class);
        http().bean(RestUserPasswordRequest.class);
        http().bean(ActivateUserRequest.class);
        http().bean(CombineSearchReservationRequest.class);
        http().bean(CombineSearchReservationResponse.class);
        http().bean(SearchRestaurantAJAXRequest.class);
        http().bean(SearchUserAJAXRequest.class);

        http().route(PUT, "/canteen/admin/login", admin::login);
        http().route(GET, "/canteen/admin/logout", admin::logout);
        http().route(POST, "/canteen/meal", meal::create); // todo move to AJAXController
        http().route(POST, "/canteen/restaurant", restaurant::create); // todo move to AJAXController
        http().route(PUT, "/canteen/restaurant/deadline", restaurant::updateDeadline); // todo move to AJAXController
        http().route(POST, "/canteen/user", user::createUser); // todo move to AJAXController
        http().route(PUT, "/canteen/user/password", user::resetPassword); // todo move to AJAXController
        http().route(PUT, "/canteen/user/status", user::activate); // todo move to AJAXController
        http().route(PUT, "/canteen/ajax/reservation", reservationAJAX::search);
        http().route(GET, "/canteen/ajax/restaurant", restaurantAJAX::search);
        http().route(GET, "/canteen/ajax/user", userAJAX::search);
    }
}
