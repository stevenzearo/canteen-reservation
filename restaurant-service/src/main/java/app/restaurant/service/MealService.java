package app.restaurant.service;

import app.restaurant.api.meal.CreateRequest;
import app.restaurant.api.meal.MealStatus;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchRequest;
import app.restaurant.api.meal.SearchResponse;
import app.restaurant.api.meal.UpdateRequest;
import app.restaurant.domain.Meal;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import core.framework.mongo.Query;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class MealService {
    @Inject
    MongoCollection<Meal> mealCollection;

    public MealView create(CreateRequest request) {
        Meal meal = new Meal();
        meal.name = request.name;
        meal.price = request.price;
        meal.status = app.restaurant.domain.MealStatus.VALID;
        meal.id = UUID.randomUUID().toString();
        meal.restaurantId = request.restaurantId;
        mealCollection.insert(meal);
        return view(meal);
    }

    public void update(String id, UpdateRequest request) {
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
            combineUpdate = Updates.combine(combineUpdate, Updates.set("status", app.restaurant.domain.RestaurantStatus.valueOf(request.status.name())));
        }
        if (request.restaurantId != null) {
            combineFilter = Filters.and(combineFilter, Filters.eq("restaurant_id", meal.restaurantId));
            combineUpdate = Updates.combine(combineUpdate, Updates.set("status", request.restaurantId));
        }
        mealCollection.update(combineFilter, combineUpdate);
    }

    public SearchResponse searchListByConditions(SearchRequest request) {
        Query query = new Query();
        query.skip = request.skip;
        query.limit = request.limit;
        Bson conditions = Filters.and();
        if (!Strings.isBlank(request.name))
            conditions = Filters.and(conditions, Filters.regex("name", request.name));
        if (request.price != null)
            conditions = Filters.and(conditions, Filters.eq("price", request.price));
        if (request.status != null)
            conditions = Filters.and(conditions, Filters.eq("status", MealStatus.valueOf(request.status.name())));
        if (request.restaurantId != null)
            conditions = Filters.and(conditions, Filters.eq("restaurant_id", request.restaurantId));
        query.filter = conditions;
        SearchResponse response = new SearchResponse();
        response.total = mealCollection.count(query.filter);
        response.mealViewList = mealCollection.find(query).stream().map(this::view).collect(Collectors.toList());
        return response;
    }

    private MealView view(Meal meal) {
        MealView mealView = new MealView();
        mealView.id = meal.id;
        mealView.name = meal.name;
        mealView.price = meal.price;
        mealView.status = meal.status == null ? null : MealStatus.valueOf(meal.status.name());
        return mealView;
    }
}
