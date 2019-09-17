package app.restaurant.service;

import app.restaurant.api.restaurant.CreateRequest;
import app.restaurant.api.restaurant.RestaurantStatus;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchRequest;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.api.restaurant.UpdateRequest;
import app.restaurant.domain.Restaurant;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import org.bson.conversions.Bson;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class RestaurantService {
    @Inject
    MongoCollection<Restaurant> restaurantCollection;

    public RestaurantView create(CreateRequest createRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.id = UUID.randomUUID().toString();
        restaurant.name = createRequest.name;
        restaurant.address = createRequest.address;
        restaurant.phone = createRequest.phone;
        restaurant.status = app.restaurant.domain.RestaurantStatus.OPEN;
        restaurant.reserveDeadline = createRequest.reserveDeadline;
        restaurantCollection.insert(restaurant);
        return view(restaurant);
    }

    public RestaurantView get(String id) {
        return view(restaurantCollection.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Restaurant not found, id = {}", id))));
    }

    public SearchResponse searchListByConditions(SearchRequest request) {
        Query query = new Query();
        query.skip = request.skip;
        query.limit = request.limit;
        Bson conditions = Filters.and();
        if (!Strings.isBlank(request.name))
            conditions = Filters.and(conditions, Filters.regex("name", request.name));
        if (!Strings.isBlank(request.address))
            conditions = Filters.and(conditions, Filters.regex("address", request.address));
        if (!Strings.isBlank(request.phone))
            conditions = Filters.and(conditions, Filters.regex("phone", request.phone));
        if (request.reserveDeadline != null)
            conditions = Filters.and(conditions, Filters.lte("reserve_deadline", request.reserveDeadline));
        if (request.status != null)
            conditions = Filters.and(conditions, Filters.eq("status", RestaurantStatus.valueOf(request.status.name())));
        query.filter = conditions;
        SearchResponse response = new SearchResponse();
        response.total = restaurantCollection.count(query.filter);
        response.restaurantViewList = restaurantCollection.find(query.filter).stream().map(this::view).collect(Collectors.toList());
        return response;
    }

    public void update(String id, UpdateRequest request) {
        Restaurant restaurant = restaurantCollection.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Restaurant not found, id = {}", id)));
        Bson combineFilter = Filters.and();
        Bson combineUpdate = Updates.combine();
        if (!Strings.isBlank(request.name)) {
            combineFilter = Filters.and(combineFilter, Filters.eq("name", restaurant.name));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("name", request.name));
        }
        if (!Strings.isBlank(request.phone)) {
            combineFilter = Filters.and(combineFilter, Filters.eq("phone", restaurant.phone));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("phone", request.phone));
        }
        if (!Strings.isBlank(request.address)) {
            combineFilter = Filters.and(combineFilter, Filters.eq("address", restaurant.address));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("address", request.address));
        }
        if (request.status != null) {
            combineFilter = Filters.and(combineFilter, Filters.eq("status", restaurant.status));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("status", app.restaurant.domain.RestaurantStatus.valueOf(request.status.name())));
        }
        if (request.reserveDeadline != null) {
            combineFilter = Filters.and(combineFilter, Filters.eq("reserve_deadline", restaurant.reserveDeadline));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("reserve_deadline", request.reserveDeadline));
        }
        restaurantCollection.update(combineFilter, combineUpdate);
    }

    private RestaurantView view(Restaurant restaurant) {
        RestaurantView restaurantView = new RestaurantView();
        restaurantView.id = restaurant.id;
        restaurantView.name = restaurant.name;
        restaurantView.address = restaurant.address;
        restaurantView.phone = restaurant.phone;
        restaurantView.status = restaurant.status == null ? null : RestaurantStatus.valueOf(restaurant.status.name());
        restaurantView.reserveDeadline = restaurant.reserveDeadline;
        return restaurantView;
    }
}
