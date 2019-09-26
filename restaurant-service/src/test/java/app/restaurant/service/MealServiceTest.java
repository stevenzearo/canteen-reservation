package app.restaurant.service;

import app.restaurant.RestaurantIntegrationExtension;
import app.restaurant.api.meal.BOCreateMealRequest;
import app.restaurant.api.meal.MealStatusView;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.BOUpdateMealRequest;
import app.restaurant.api.restaurant.BOCreateRestaurantRequest;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.domain.Meal;
import app.restaurant.domain.Restaurant;
import com.mongodb.client.model.Filters;
import core.framework.inject.Inject;
import core.framework.mongo.MongoCollection;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author steve
 */
class MealServiceTest extends RestaurantIntegrationExtension {
    @Inject
    MealService mealService;

    @Inject
    MongoCollection<Meal> mealMongoCollection;

    @Inject
    MongoCollection<Restaurant> restaurantMongoCollection;

    @Inject
    RestaurantService restaurantService;

    String mealId;

    String restaurantId;

    @BeforeEach
    void create() {
        BOCreateRestaurantRequest BOCreateRestaurantRequest = new BOCreateRestaurantRequest();
        BOCreateRestaurantRequest.name = "KFC";
        BOCreateRestaurantRequest.address = "Roma Road";
        BOCreateRestaurantRequest.phone = "4008123123";
        BOCreateRestaurantRequest.reservingDeadline = ZonedDateTime.now().plusDays(3);

        BOCreateMealRequest BOCreateMealRequest = new BOCreateMealRequest();
        BOCreateMealRequest.name = "noddles";
        BOCreateMealRequest.price = 20.39;
    }

    @Test
    void update() {
        BOUpdateMealRequest BOUpdateMealRequest = new BOUpdateMealRequest();
        BOUpdateMealRequest.status = MealStatusView.INVALID;
        SearchMealRequest searchMealRequest = new SearchMealRequest();
        searchMealRequest.status = MealStatusView.INVALID;
        searchMealRequest.status = MealStatusView.VALID;
    }

    @Test
    void searchListByConditions() {
        SearchMealRequest searchMealRequest = new SearchMealRequest();
        searchMealRequest.status = MealStatusView.VALID;
        searchMealRequest.status = MealStatusView.INVALID;
    }

    @AfterEach
    void delete() {
        mealMongoCollection.delete(Filters.eq("meal.id", mealId));
        restaurantMongoCollection.delete(Filters.eq("id", restaurantId));
        mealMongoCollection.get(mealId);
        assertThatExceptionOfType(NotFoundException.class);
        restaurantMongoCollection.get(mealId);
        assertThatExceptionOfType(NotFoundException.class);
    }
}