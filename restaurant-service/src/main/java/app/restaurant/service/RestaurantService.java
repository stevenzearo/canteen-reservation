package app.restaurant.service;

import app.restaurant.api.meal.SearchRequest;
import app.restaurant.api.meal.SearchResponse;
import app.restaurant.api.meal.UpdateRequest;
import app.restaurant.api.restaurant.CreateRequest;
import app.restaurant.api.restaurant.CreateResponse;
import app.restaurant.api.restaurant.RestaurantView;
import org.bson.types.ObjectId;

/**
 * @author steve
 */
public class RestaurantService {
    public CreateResponse create(CreateRequest createRequest) {
        return null;
    }

    public RestaurantView get(ObjectId id) {
        return null;
    }

    public SearchResponse searchByConditions(SearchRequest request) {
        return null;
    }

    public void update(UpdateRequest request) {

    }
}
