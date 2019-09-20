package app.restaurant.web;

import app.restaurant.api.BOMealWebService;
import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.CreateMealResponse;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.UpdateMealRequest;
import app.restaurant.service.MealService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BOMealWebServiceImpl implements BOMealWebService {
    @Inject
    MealService service;

    @Override
    public CreateMealResponse create(String restaurantId, CreateMealRequest request) {
        return service.create(restaurantId, request);
    }

    @Override
    public void update(String restaurantId, String id, UpdateMealRequest request) {
        service.update(restaurantId, id, request);
    }

    @Override
    public SearchMealResponse search(String restaurantId, SearchMealRequest request) {
        return service.search(restaurantId, request);
    }
}
