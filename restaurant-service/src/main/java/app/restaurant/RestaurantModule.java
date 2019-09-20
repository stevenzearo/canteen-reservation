package app.restaurant;

import app.restaurant.api.BOMealWebService;
import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.MealWebService;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.MealStatusView;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.UpdateMealRequest;
import app.restaurant.api.restaurant.CreateRestaurantRequest;
import app.restaurant.api.restaurant.RestaurantStatusView;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.api.restaurant.UpdateRestaurantRequest;
import app.restaurant.service.MealService;
import app.restaurant.service.RestaurantService;
import app.restaurant.web.BOMealWebServiceImpl;
import app.restaurant.web.BORestaurantWebServiceImpl;
import app.restaurant.web.MealWebServiceImpl;
import app.restaurant.web.RestaurantWebServiceImpl;
import core.framework.module.Module;

/**
 * @author steve
 */
public class RestaurantModule extends Module {
    @Override
    protected void initialize() {
        bind(MealService.class);
        bind(RestaurantService.class);
        api().service(MealWebService.class, bind(MealWebServiceImpl.class));
        api().service(BOMealWebService.class, bind(BOMealWebServiceImpl.class));
        api().service(RestaurantWebService.class, bind(RestaurantWebServiceImpl.class));
        api().service(BORestaurantWebService.class, bind(BORestaurantWebServiceImpl.class));
    }
}