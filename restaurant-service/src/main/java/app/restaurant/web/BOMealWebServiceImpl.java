package app.restaurant.web;

import app.restaurant.api.BOMealWebService;
import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.BOCreateMealResponse;
import app.restaurant.api.meal.BOSearchMealRequest;
import app.restaurant.api.meal.BOSearchMealResponse;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.BOUpdateMealRequest;
import app.restaurant.service.BOMealService;
import app.restaurant.service.MealService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BOMealWebServiceImpl implements BOMealWebService {
    @Inject
    BOMealService service;

    @Override
    public BOCreateMealResponse create(String restaurantId, BOCreateMealRequest request) {
        return service.create(restaurantId, request);
    }

    @Override
    public void update(String restaurantId, String id, BOUpdateMealRequest request) {
        service.update(restaurantId, id, request);
    }

    @Override
    public BOSearchMealResponse search(String restaurantId, BOSearchMealRequest request) {
        return service.search(restaurantId, request);
    }
}
