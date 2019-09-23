package app.restaurant.web;

import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.GetRestaurantResponse;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchResponse;
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
    public SearchResponse search(SearchRestaurantRequest request) {
        return service.search(request);
    }
}