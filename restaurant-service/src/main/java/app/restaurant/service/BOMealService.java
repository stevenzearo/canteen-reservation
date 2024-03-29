package app.restaurant.service;

import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.BOCreateMealResponse;
import app.restaurant.api.meal.BOGetMealResponse;
import app.restaurant.api.meal.BOSearchMealRequest;
import app.restaurant.api.meal.BOSearchMealResponse;
import app.restaurant.api.meal.BOUpdateMealRequest;
import app.restaurant.api.meal.MealStatusView;
import app.restaurant.domain.Meal;
import app.restaurant.domain.MealStatus;
import app.restaurant.domain.Restaurant;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Strings;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;
import org.bson.conversions.Bson;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class BOMealService {
    @Inject
    MongoCollection<Meal> mealCollection;

    @Inject
    MongoCollection<Restaurant> restaurantCollection;

    public BOCreateMealResponse create(String restaurantId, BOCreateMealRequest request) {
        Meal meal = new Meal();
        meal.name = request.name;
        meal.price = request.price;
        meal.status = MealStatus.VALID;
        meal.id = UUID.randomUUID().toString();
        meal.restaurantId = restaurantId;
        mealCollection.insert(meal);
        BOCreateMealResponse response = new BOCreateMealResponse();
        response.id = meal.id;
        response.name = meal.name;
        response.price = meal.price;
        response.status = MealStatusView.valueOf(meal.status.name());
        return response;
    }

    public void update(String restaurantId, String id, BOUpdateMealRequest request) {
        restaurantCollection.get(restaurantId).orElseThrow(() -> new NotFoundException(Strings.format("Restaurant not found, id = {}", restaurantId)));
        Meal meal = mealCollection.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Meal not found, id = {}", id)));
        Bson combineFilter = Filters.and();
        Bson combineUpdate = Updates.combine();
        if (!Strings.isBlank(request.name)) {
            combineFilter = Filters.and(combineFilter, Filters.eq("name", meal.name));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("name", request.name));
        }
        if (request.price != null) {
            combineFilter = Filters.and(combineFilter, Filters.eq("price", meal.price));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("price", request.price));
        }
        if (request.status != null) {
            combineFilter = Filters.and(combineFilter, Filters.eq("status", meal.status));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("status", MealStatus.valueOf(request.status.name())));
        }
        mealCollection.update(combineFilter, combineUpdate);
    }

    public BOGetMealResponse get(String restaurantId, String mealId) {
        Meal meal = mealCollection.get(mealId).orElseThrow(() -> new NotFoundException(Strings.format("meal not found, id = {}", mealId)));
        if (!meal.restaurantId.equals(restaurantId)) {
            throw new ConflictException(Strings.format("meal miss match with the restaurant id, id = {}", restaurantId));
        }
        BOGetMealResponse response = new BOGetMealResponse();
        response.id = meal.id;
        response.name = meal.name;
        response.price = meal.price;
        response.status = MealStatusView.valueOf(meal.status.name());
        return response;
    }

    public BOSearchMealResponse search(String restaurantId, BOSearchMealRequest request) {
        Query query = new Query();
        query.skip = request.skip;
        query.limit = request.limit;
        Bson conditions = Filters.and(Filters.eq("restaurant_id", restaurantId));
        if (!Strings.isBlank(request.name))
            conditions = Filters.and(conditions, Filters.regex("name", request.name));
        if (request.priceStart != null)
            conditions = Filters.and(conditions, Filters.gte("price", request.priceStart));
        if (request.priceEnd != null)
            conditions = Filters.and(conditions, Filters.lte("price", request.priceEnd));
        if (request.status != null)
            conditions = Filters.and(conditions, Filters.eq("status", MealStatus.valueOf(request.status.name())));
        query.filter = conditions;
        BOSearchMealResponse response = new BOSearchMealResponse();
        response.total = mealCollection.count(query.filter);
        response.meals = mealCollection.find(query).stream().map(this::view).collect(Collectors.toList());
        return response;
    }

    private BOSearchMealResponse.Meal view(Meal meal) {
        BOSearchMealResponse.Meal mealView = new BOSearchMealResponse.Meal();
        mealView.id = meal.id;
        mealView.name = meal.name;
        mealView.price = meal.price;
        mealView.status = meal.status == null ? null : MealStatusView.valueOf(meal.status.name());
        return mealView;
    }
}