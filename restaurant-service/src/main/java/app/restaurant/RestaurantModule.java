package app.restaurant;

import app.restaurant.api.MealWebService;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.MealStatus;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.UpdateMealRequest;
import app.restaurant.api.restaurant.CreateRestaurantRequest;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.api.restaurant.UpdateRestaurantRequest;
import app.restaurant.service.MealService;
import app.restaurant.service.RestaurantService;
import app.restaurant.web.MealWebServiceImpl;
import app.restaurant.web.RestaurantWebServiceImpl;
import core.framework.module.Module;

/**
 * @author steve
 */
public class RestaurantModule extends Module {
    @Override
    protected void initialize() {
        http().bean(MealView.class);
        http().bean(CreateMealRequest.class);
        http().bean(MealStatus.class);
        http().bean(SearchMealRequest.class);
        http().bean(SearchMealResponse.class);
        http().bean(UpdateMealRequest.class);

        http().bean(RestaurantView.class);
        http().bean(app.restaurant.api.restaurant.RestaurantStatus.class);
        http().bean(CreateRestaurantRequest.class);
        http().bean(SearchRestaurantRequest.class);
        http().bean(app.restaurant.api.restaurant.SearchResponse.class);
        http().bean(UpdateRestaurantRequest.class);

        bind(MealService.class);
        bind(RestaurantService.class);

        api().service(MealWebService.class, bind(MealWebServiceImpl.class));
        api().service(RestaurantWebService.class, bind(RestaurantWebServiceImpl.class));
    }
}