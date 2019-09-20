package app.restaurant.web;

import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.CreateRestaurantRequest;
import app.restaurant.api.restaurant.CreateRestaurantResponse;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.api.restaurant.UpdateRestaurantRequest;
import app.restaurant.service.RestaurantService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BORestaurantWebServiceImpl implements BORestaurantWebService {
    @Inject
    RestaurantService service;

    @Override
    public CreateRestaurantResponse create(CreateRestaurantRequest request) {
        return service.create(request);
    }

    @Override
    public RestaurantView get(String id) {
        return service.get(id);
    }

    @Override
    public SearchResponse search(SearchRestaurantRequest request) {
        return service.search(request);
    }

    @Override
    public void update(String id, UpdateRestaurantRequest request) {
        service.update(id, request);
    }
}