package app.canteen;

import app.canteen.service.CombineSearchReservationService;
import app.canteen.service.reservation.CombineSearchReservationRequest;
import app.canteen.service.reservation.CombineSearchReservationResponse;
import app.canteen.web.ajax.ReservationAJAXController;
import app.canteen.web.ajax.RestaurantAJAXController;
import app.canteen.web.ajax.UserAJAXController;
import app.canteen.web.ajax.restaurant.SearchRestaurantAJAXRequest;
import app.canteen.web.ajax.user.SearchUserAJAXRequest;
import app.canteen.web.ajax.user.UserStatusAJAXView;
import app.canteen.web.controller.AdminController;
import app.canteen.web.controller.MealController;
import app.canteen.web.controller.RestaurantController;
import app.canteen.web.controller.UserController;
import app.canteen.web.controller.admin.AdminLoginControllerRequest;
import app.canteen.web.controller.meal.CreateMealControllerRequest;
import app.canteen.web.controller.restaurant.CreateRestaurantControllerRequest;
import app.canteen.web.controller.restaurant.UpdateRestaurantControllerRequest;
import app.canteen.web.controller.user.ActivateUserControllerRequest;
import app.canteen.web.controller.user.CreateUserControllerRequest;
import app.canteen.web.controller.user.RestUserPasswordControllerRequest;
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
        bind(CombineSearchReservationService.class);
        AdminController admin = bind(AdminController.class);
        UserController user = bind(UserController.class);
        RestaurantController restaurant = bind(RestaurantController.class);
        MealController meal = bind(MealController.class);
        UserAJAXController userJAX = bind(UserAJAXController.class);
        RestaurantAJAXController restaurantAJAX = bind(RestaurantAJAXController.class);
        ReservationAJAXController reservationAJAX = bind(ReservationAJAXController.class);
        site().session().timeout(Duration.ofMinutes(30));
        http().intercept(bind(CanteenBOServiceInterceptor.class));

        http().bean(AdminLoginControllerRequest.class);
        http().bean(CreateMealControllerRequest.class);
        http().bean(CreateRestaurantControllerRequest.class);
        http().bean(UpdateRestaurantControllerRequest.class);
        http().bean(CreateUserControllerRequest.class);
        http().bean(RestUserPasswordControllerRequest.class);
        http().bean(ActivateUserControllerRequest.class);
        http().bean(CombineSearchReservationRequest.class);
        http().bean(SearchRestaurantAJAXRequest.class);
        http().bean(SearchUserAJAXRequest.class);
        
        http().route(PUT, "/canteen/bo/admin/login", admin::login);
        http().route(GET, "/canteen/bo/admin/logout", admin::logout);
        http().route(POST, "/canteen/bo/meal", meal::create);
        http().route(POST, "/canteen/bo/restaurant", restaurant::create);
        http().route(PUT, "/canteen/bo/restaurant/deadline", restaurant::updateDeadline);
        http().route(POST, "/canteen/bo/user", user::createUser);
        http().route(PUT, "/canteen/bo/user/password", user::resetPassword);
        http().route(PUT, "/canteen/bo/user/status", user::activate);
        http().route(PUT, "/canteen/bo/ajax/reservation", reservationAJAX::search);
        http().route(GET, "/canteen/bo/ajax/restaurant", restaurantAJAX::search);
        http().route(GET, "/canteen/bo/ajax/user", userJAX::search);
        http().bean(CombineSearchReservationResponse.class);
    }
}
