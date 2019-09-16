package app.restaurant.web;

import app.restaurant.api.RestaurantWebService;
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
public class RestaurantWebServiceImpl implements RestaurantWebService {
    @Override
    public CreateResponse create(CreateRequest createRequest) {
        return null;
    }

    @Override
    public RestaurantView get(ObjectId id) {
        return null;
    }

    @Override
    public SearchResponse searchByConditions(SearchRequest request) {
        return null;
    }

    @Override
    public void update(UpdateRequest request) {

    }
}
