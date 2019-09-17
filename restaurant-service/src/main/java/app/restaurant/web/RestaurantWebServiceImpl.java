package app.restaurant.web;

import app.restaurant.api.RestaurantWebService;
import app.restaurant.api.restaurant.CreateRequest;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchRequest;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.service.RestaurantService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class RestaurantWebServiceImpl implements RestaurantWebService {
    @Inject
    RestaurantService service;

    @Override
    public RestaurantView create(CreateRequest request) {
        return service.create(request);
    }

    @Override
    public RestaurantView get(String id) {
        return service.get(id);
    }

    @Override
    public SearchResponse searchListByConditions(SearchRequest request) {
        return service.searchListByConditions(request);
    }

    @Override
    public void update(String id, app.restaurant.api.restaurant.UpdateRequest request) {
        service.update(id, request);
    }
}
