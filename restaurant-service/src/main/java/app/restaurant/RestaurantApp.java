package app.restaurant;

import app.restaurant.domain.Meal;
import app.restaurant.domain.Restaurant;
import core.framework.module.App;
import core.framework.module.SystemModule;
import core.framework.mongo.module.MongoConfig;

/**
 * @author steve
 */
public class RestaurantApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));

        String uri = requiredProperty("sys.mongo.uri");
        MongoConfig config = config(MongoConfig.class);
        config.uri(uri);
        config.collection(Meal.class);
        config.collection(Restaurant.class);

        load(new RestaurantModule());
    }
}