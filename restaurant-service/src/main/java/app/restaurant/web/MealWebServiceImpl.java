package app.restaurant.web;

import app.restaurant.api.MealWebService;
import app.restaurant.api.meal.CreateRequest;
import app.restaurant.api.meal.SearchRequest;
import app.restaurant.api.meal.SearchResponse;
import app.restaurant.api.meal.UpdateRequest;
import org.bson.types.ObjectId;

/**
 * @author steve
 */
public class MealWebServiceImpl implements MealWebService {
    @Override
    public CreateRequest create(CreateRequest request) {
        return null;
    }

    @Override
    public void update(ObjectId id, UpdateRequest request) {

    }

    @Override
    public SearchResponse searchByConditions(SearchRequest request) {
        return null;
    }
}
