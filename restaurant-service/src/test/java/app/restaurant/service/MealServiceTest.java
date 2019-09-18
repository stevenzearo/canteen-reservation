package app.restaurant.service;

import app.restaurant.RestaurantIntegrationExtension;
import app.restaurant.api.meal.CreateMealRequest;
import app.restaurant.api.meal.MealStatus;
import app.restaurant.api.meal.MealView;
import app.restaurant.api.meal.SearchMealRequest;
import app.restaurant.api.meal.SearchMealResponse;
import app.restaurant.api.meal.UpdateMealRequest;
import app.restaurant.api.restaurant.CreateRestaurantRequest;
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
        CreateRestaurantRequest createRestaurantRequest = new CreateRestaurantRequest();
        createRestaurantRequest.name = "KFC";
        createRestaurantRequest.address = "Roma Road";
        createRestaurantRequest.phone = "4008123123";
        createRestaurantRequest.reserveDeadline = ZonedDateTime.now().plusDays(3);
        RestaurantView restaurantView = restaurantService.create(createRestaurantRequest);
        assertThat(restaurantView.name).isEqualTo("KFC");
        assertThat(restaurantView.name).isNotEqualTo("Kfc");
        assertThat(restaurantView.address).isEqualTo("Roma Road");
        assertThat(restaurantView.phone).isEqualTo("4008123123");
        restaurantId = restaurantView.id;

        CreateMealRequest createMealRequest = new CreateMealRequest();
        createMealRequest.name = "noddles";
        createMealRequest.price = 20.39;
        createMealRequest.restaurantId = restaurantId;
        MealView mealView = mealService.create(createMealRequest);
        assertThat(mealView.id).isNotEmpty();
        assertThat(mealView.name).isEqualTo("noddles");
        mealId = mealView.id;
    }

    @Test
    void update() {
        UpdateMealRequest updateMealRequest = new UpdateMealRequest();
        updateMealRequest.status = MealStatus.INVALID;
        mealService.update(mealId, updateMealRequest);
        SearchMealRequest searchMealRequest = new SearchMealRequest();
        searchMealRequest.status = MealStatus.INVALID;
        SearchMealResponse searchMealResponse = mealService.searchListByConditions(searchMealRequest);
        assertThat(searchMealResponse.mealViewList).size().isGreaterThan(0);
        searchMealRequest.status = MealStatus.VALID;
        SearchMealResponse searchMealResponse1 = mealService.searchListByConditions(searchMealRequest);
        assertThat(searchMealResponse1.mealViewList).size().isEqualTo(0);
    }

    @Test
    void searchListByConditions() {
        SearchMealRequest searchMealRequest = new SearchMealRequest();
        searchMealRequest.status = MealStatus.VALID;
        SearchMealResponse searchMealResponse = mealService.searchListByConditions(searchMealRequest);
        assertThat(searchMealResponse.mealViewList).size().isGreaterThan(0);
        searchMealRequest.status = MealStatus.INVALID;
        SearchMealResponse searchMealResponse1 = mealService.searchListByConditions(searchMealRequest);
        assertThat(searchMealResponse1.mealViewList).size().isEqualTo(0);
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