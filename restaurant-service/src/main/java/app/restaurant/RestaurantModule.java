package app.restaurant;

import app.restaurant.api.MealWebService;
import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.meal.CreateRequest;
import app.restaurant.api.meal.MealStatus;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchRequest;
import app.restaurant.api.meal.SearchResponse;
import app.restaurant.api.meal.UpdateRequest;
import app.restaurant.api.restaurant.RestaurantStatus;
import app.restaurant.api.restaurant.RestaurantView;
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
        http().bean(CreateRequest.class);
        http().bean(MealStatus.class);
        http().bean(SearchRequest.class);
        http().bean(SearchResponse.class);
        http().bean(UpdateRequest.class);

        http().bean(RestaurantView.class);
        http().bean(RestaurantStatus.class);
        http().bean(app.restaurant.api.restaurant.CreateRequest.class);
        http().bean(app.restaurant.api.restaurant.SearchRequest.class);
        http().bean(app.restaurant.api.restaurant.SearchResponse.class);
        http().bean(app.restaurant.api.restaurant.UpdateRequest.class);

        bind(MealService.class);
        bind(RestaurantService.class);

        api().service(MealWebService.class, bind(MealWebServiceImpl.class));
        api().service(RestaurantWebService.class, bind(RestaurantWebServiceImpl.class));
    }
}
