package app.restaurant.service;

import app.restaurant.api.restaurant.GetRestaurantResponse;
import app.restaurant.api.restaurant.RestaurantStatusView;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.api.restaurant.SearchRestaurantResponse;
import app.restaurant.domain.Restaurant;
import app.restaurant.domain.RestaurantStatus;
import com.mongodb.client.model.Filters;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import org.bson.conversions.Bson;

import java.util.stream.Collectors;

/**
 * @author steve
 */
public class RestaurantService {
    @Inject
    MongoCollection<Restaurant> restaurantCollection;

    public GetRestaurantResponse get(String id) {
        Restaurant restaurant = restaurantCollection.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Restaurant not found, id = {}", id)));
        GetRestaurantResponse response = new GetRestaurantResponse();
        response.id = restaurant.id;
        response.name = restaurant.name;
        response.address = restaurant.address;
        response.phone = restaurant.phone;
        response.status = RestaurantStatusView.valueOf(restaurant.status.name());
        response.reservingDeadline = restaurant.reservingDeadline;
        return response;
    }

    public SearchRestaurantResponse searchOpen(SearchRestaurantRequest request) {
        Query query = new Query();
        query.skip = request.skip;
        query.limit = request.limit;
        Bson conditions = Filters.exists("_id");
        conditions = Filters.and(conditions, Filters.eq("status", RestaurantStatus.OPEN));
        if (!Strings.isBlank(request.name))
            conditions = Filters.and(conditions, Filters.regex("name", request.name));
        if (!Strings.isBlank(request.address))
            conditions = Filters.and(conditions, Filters.regex("address", request.address));
        if (!Strings.isBlank(request.phone))
            conditions = Filters.and(conditions, Filters.regex("phone", request.phone));
        if (request.reservingDeadlineStart != null)
            conditions = Filters.and(conditions, Filters.gt("reserving_deadline", request.reservingDeadlineStart));
        if (request.reservingDeadlineEnd != null)
            conditions = Filters.and(conditions, Filters.lt("reserving_deadline", request.reservingDeadlineEnd.plusMinutes(1)));
        query.filter = conditions;
        SearchRestaurantResponse response = new SearchRestaurantResponse();
        response.total = restaurantCollection.count(query.filter);
        response.restaurants = restaurantCollection.find(query.filter).stream().map(this::view).collect(Collectors.toList());
        return response;
    }

    private SearchRestaurantResponse.Restaurant view(Restaurant restaurant) {
        SearchRestaurantResponse.Restaurant restaurantView = new SearchRestaurantResponse.Restaurant();
        restaurantView.id = restaurant.id;
        restaurantView.name = restaurant.name;
        restaurantView.address = restaurant.address;
        restaurantView.phone = restaurant.phone;
        restaurantView.reservingDeadline = restaurant.reservingDeadline;
        return restaurantView;
    }
}