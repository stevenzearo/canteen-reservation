package app.restaurant.web;

import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.CreateRestaurantRequest;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.api.restaurant.UpdateRestaurantRequest;
import app.restaurant.service.RestaurantService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class RestaurantWebServiceImpl implements RestaurantWebService {
    @Inject
    RestaurantService service;

    @Override
    public RestaurantView create(CreateRestaurantRequest request) {
        return service.create(request);
    }

    @Override
    public RestaurantView get(String id) {
        return service.get(id);
    }

    @Override
    public SearchResponse searchListByConditions(SearchRestaurantRequest request) {
        return service.searchListByConditions(request);
    }

    @Override
    public void update(String id, UpdateRestaurantRequest request) {
        service.update(id, request);
    }
}