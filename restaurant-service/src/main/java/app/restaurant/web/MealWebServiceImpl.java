package app.restaurant.web;

import app.restaurant.api.MealWebService;
import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.UpdateMealRequest;
import app.restaurant.service.MealService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class MealWebServiceImpl implements MealWebService {
    @Inject
    MealService service;

    @Override
    public MealView create(CreateMealRequest request) {
        return service.create(request);
    }

    @Override
    public void update(String id, UpdateMealRequest request) {
        service.update(id, request);
    }

    @Override
    public SearchMealResponse searchListByConditions(SearchMealRequest request) {
        return service.searchListByConditions(request);
    }
}
