package app.restaurant.service;

import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.BOCreateMealResponse;
import app.restaurant.api.meal.MealStatusView;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.BOUpdateMealRequest;
import app.restaurant.domain.Meal;
import app.restaurant.domain.MealStatus;
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
public class MealService {
    @Inject
    MongoCollection<Meal> mealCollection;

    public SearchMealResponse search(String restaurantId, SearchMealRequest request) {
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
        SearchMealResponse response = new SearchMealResponse();
        response.total = mealCollection.count(query.filter);
        response.meals = mealCollection.find(query).stream().map(this::view).collect(Collectors.toList());
        return response;
    }

    private SearchMealResponse.MealView view(Meal meal) {
        SearchMealResponse.MealView mealView = new SearchMealResponse.MealView();
        mealView.id = meal.id;
        mealView.name = meal.name;
        mealView.price = meal.price;
        mealView.status = meal.status == null ? null : MealStatusView.valueOf(meal.status.name());
        return mealView;
    }
}