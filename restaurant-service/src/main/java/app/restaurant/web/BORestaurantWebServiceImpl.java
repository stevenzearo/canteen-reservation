package app.restaurant.web;

import app.restaurant.api.BORestaurantWebService;
import app.restaurant.api.restaurant.BOCreateRestaurantRequest;
import app.restaurant.api.restaurant.BOCreateRestaurantResponse;
import app.restaurant.api.restaurant.BOGetRestaurantResponse;
import app.restaurant.api.restaurant.BOSearchRestaurantResponse;
import app.restaurant.api.restaurant.BOSearchRestaurantRequest;
import app.restaurant.api.restaurant.BOUpdateRestaurantRequest;
import app.restaurant.service.BORestaurantService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BORestaurantWebServiceImpl implements BORestaurantWebService {
    @Inject
    BORestaurantService service;

    @Override
    public BOCreateRestaurantResponse create(BOCreateRestaurantRequest request) {
        return service.create(request);
    }

    @Override
    public BOGetRestaurantResponse get(String id) {
        return service.get(id);
    }

    @Override
    public BOSearchRestaurantResponse search(BOSearchRestaurantRequest request) {
        return service.search(request);
    }

    @Override
    public void update(String id, BOUpdateRestaurantRequest request) {
        service.update(id, request);
    }
}