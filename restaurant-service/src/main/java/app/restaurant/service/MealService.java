package app.restaurant.service;

import app.restaurant.api.meal.CreateRequest;
import app.restaurant.api.meal.SearchRequest;
import app.restaurant.api.meal.SearchResponse;
import app.restaurant.api.meal.UpdateRequest;
import org.bson.types.ObjectId;

/**
 * @author steve
 */
public class MealService {
    public CreateRequest create(CreateRequest request) {
        return null;
    }

    public void update(ObjectId id, UpdateRequest request) {

    }

    public SearchResponse searchByConditions(SearchRequest request) {
        return null;
    }
}
