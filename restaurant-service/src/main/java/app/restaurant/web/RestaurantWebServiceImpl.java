package app.restaurant.web;

import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.GetRestaurantResponse;
import app.restaurant.api.restaurant.SearchRestaurantResponse;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.service.RestaurantService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class RestaurantWebServiceImpl implements RestaurantWebService {
    @Inject
    RestaurantService service;

    @Override
    public GetRestaurantResponse get(String id) {
        return service.get(id);
    }

    @Override
    public SearchRestaurantResponse searchOpen(SearchRestaurantRequest request) {
        return service.searchOpen(request);
    }
}