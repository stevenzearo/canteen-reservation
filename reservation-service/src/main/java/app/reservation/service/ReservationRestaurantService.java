package app.reservation.service;

import app.reservation.domain.ReservationRestaurant;
import app.reservation.service.restaurant.CreateReservationRestaurantRequest;
import core.framework.db.Repository;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class ReservationRestaurantService {
    @Inject
    Repository<ReservationRestaurant> repository;
    
    void create(CreateReservationRestaurantRequest request) {
        ReservationRestaurant restaurant = new ReservationRestaurant();
        restaurant.reservationId = request.reservationId;
        restaurant.restaurantId = request.restaurantId;
        restaurant.restaurantName = request.restaurantName;
        restaurant.restaurantPhone = request.restaurantPhone;
        repository.insert(restaurant);
    }
}
