package app.restaurant.web;

import app.restaurant.api.MealWebService;
import app.restaurant.api.meal.CreateRequest;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchRequest;
import app.restaurant.api.meal.SearchResponse;
import app.restaurant.api.meal.UpdateRequest;
import app.restaurant.service.MealService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class MealWebServiceImpl implements MealWebService {
    @Inject
    MealService service;

    @Override
    public MealView create(CreateRequest request) {
        return service.create(request);
    }

    @Override
    public void update(String id, UpdateRequest request) {
        service.update(id, request);
    }

    @Override
    public SearchResponse searchListByConditions(SearchRequest request) {
        return service.searchListByConditions(request);
    }
}
