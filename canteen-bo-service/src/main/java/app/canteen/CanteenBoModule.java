package app.canteen;

import app.canteen.web.ajax.ReservationBOAJAXController;
import app.canteen.web.ajax.RestaurantBOAJAXController;
import app.canteen.web.ajax.UserBOAJAXController;
import app.canteen.web.controller.AdminBOController;
import app.canteen.web.controller.MealBOController;
import app.canteen.web.controller.RestaurantBOController;
import app.canteen.web.controller.UserBOController;
import app.reservation.api.ReservationWebService;
import app.restaurant.api.MealWebService;
import app.restaurant.api.RestaurantWebService;
import app.user.api.BOAdminWebService;
import app.user.api.BOUserWebService;
import app.user.api.UserWebService;
import core.framework.module.Module;

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
        api().client(RestaurantWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(MealWebService.class, requiredProperty("app.restaurant.serviceURL"));
        api().client(ReservationWebService.class, requiredProperty("app.reservation.serviceURL"));
        AdminBOController admin = bind(AdminBOController.class);
        UserBOController user = bind(UserBOController.class);
        RestaurantBOController restaurant = bind(RestaurantBOController.class);
        MealBOController meal = bind(MealBOController.class);
        UserBOAJAXController userJAX = bind(UserBOAJAXController.class);
        RestaurantBOAJAXController restaurantAJAX = bind(RestaurantBOAJAXController.class);
        ReservationBOAJAXController reservationAJAX = bind(ReservationBOAJAXController.class);
        http().route(POST, "/canteen/bo/login", admin::login);
        http().route(POST, "/canteen/bo/meal/create", meal::create);
        http().route(POST, "/canteen/bo/restaurant/create", restaurant::create);
        http().route(POST, "/canteen/bo/restaurant/deadline", restaurant::updateDeadline);
        http().route(POST, "/canteen/bo/user/creat", user::createUser);
        http().route(PUT, "/canteen/bo/user/reset-password", user::resetPassword);
        http().route(GET, "/canteen/bo/ajax/reservation/search", reservationAJAX::searchListByPage);
        http().route(PUT, "/canteen/bo/ajax/reservation/search", reservationAJAX::searchListByConditionsAndPage);
        http().route(GET, "/canteen/bo/ajax/restaurant/search", restaurantAJAX::searchListByPage);
        http().route(GET, "/canteen/bo/ajax/user/search", userJAX::searchListByPage);
    }
}
