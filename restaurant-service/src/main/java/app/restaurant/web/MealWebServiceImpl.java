package app.restaurant.web;

import app.restaurant.api.MealWebService;
import app.restaurant.api.meal.GetMealResponse;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.service.MealService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class MealWebServiceImpl implements MealWebService {
    @Inject
    MealService service;

    @Override
    public GetMealResponse get(String restaurantId, String mealId) {
        return service.get(restaurantId, mealId);
    }

    @Override
    public SearchMealResponse searchValid(String restaurantId, SearchMealRequest request) {
        return service.searchValid(restaurantId, request);
    }
}
