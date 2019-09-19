package app.restaurant.service;

import app.restaurant.RestaurantIntegrationExtension;
import app.restaurant.api.restaurant.CreateRestaurantRequest;
import app.restaurant.api.restaurant.RestaurantStatus;
import app.restaurant.api.restaurant.RestaurantView;
import app.restaurant.api.restaurant.SearchResponse;
import app.restaurant.api.restaurant.SearchRestaurantRequest;
import app.restaurant.api.restaurant.UpdateRestaurantRequest;
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
class RestaurantServiceTest extends RestaurantIntegrationExtension {
    @Inject
    RestaurantService restaurantService;

    @Inject
    MongoCollection<Restaurant> restaurantMongoCollection;

    String restaurantId;

    @BeforeEach
    void create() {
        CreateRestaurantRequest request = new CreateRestaurantRequest();
        request.reserveDeadline = ZonedDateTime.now().plusDays(3);
        request.phone = "4008123123";
        request.name = "KFC";
        request.address = "Roma Road";
        request.phone = "4008123123";
        request.reserveDeadline = ZonedDateTime.now().plusDays(3);
        RestaurantView restaurantView = restaurantService.create(request);
        assertThat(restaurantView.name).isEqualTo("KFC");
        assertThat(restaurantView.name).isNotEqualTo("Kfc");
        assertThat(restaurantView.address).isEqualTo("Roma Road");
        assertThat(restaurantView.phone).isEqualTo("4008123123");
        restaurantId = restaurantView.id;
    }

    @Test
    void get() {
        RestaurantView restaurantView = restaurantService.get(restaurantId);
        assertThat(restaurantView.id).isEqualTo(restaurantId);
        assertThat(restaurantView.name).isEqualTo("KFC");
        assertThat(restaurantView.name).isNotEqualTo("Kfc");
        assertThat(restaurantView.address).isEqualTo("Roma Road");
        assertThat(restaurantView.phone).isEqualTo("4008123123");
    }

    @Test
    void searchListByConditions() {
        SearchRestaurantRequest request = new SearchRestaurantRequest();
        request.reserveDeadlineLaterThan = ZonedDateTime.now().plusDays(2);
        request.status = RestaurantStatus.OPEN;
        SearchResponse response = restaurantService.searchListByConditions(request);
        assertThat(response.total).isGreaterThan(0);
        assertThat(response.restaurantViewList).size().isGreaterThan(0);
    }

    @Test
    void update() {
        UpdateRestaurantRequest updateRequest = new UpdateRestaurantRequest();
        updateRequest.reserveDeadline = ZonedDateTime.now().plusDays(2);
        restaurantService.update(restaurantId, updateRequest);
        SearchRestaurantRequest searchRequest = new SearchRestaurantRequest();
        searchRequest.status = RestaurantStatus.OPEN;
        searchRequest.reserveDeadlineLaterThan = ZonedDateTime.now().plusDays(2);
        SearchResponse response = restaurantService.searchListByConditions(searchRequest);
        assertThat(response.total).isEqualTo(0);
        assertThat(response.restaurantViewList).size().isEqualTo(0);
    }

    @AfterEach
    void delete() {
        restaurantMongoCollection.delete(Filters.eq("id", restaurantId));
        restaurantMongoCollection.get(restaurantId);
        assertThatExceptionOfType(NotFoundException.class);
    }
}