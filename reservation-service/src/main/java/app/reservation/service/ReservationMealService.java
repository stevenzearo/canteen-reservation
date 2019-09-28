package app.reservation.service;

import app.reservation.domain.ReservationMeal;
import app.reservation.service.meal.CreateReservationMealRequest;
import core.framework.db.Repository;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class ReservationMealService {
    @Inject
    Repository<ReservationMeal> reservationMealRepository;
    
    void create(CreateReservationMealRequest request) {
        ReservationMeal reservationMeal = new ReservationMeal();
        reservationMeal.reservationId = request.reservationId;
        reservationMeal.mealId = request.mealId;
        reservationMeal.mealPrice = request.mealPrice;
        reservationMeal.mealName = request.mealName;
        reservationMealRepository.insert(reservationMeal);
    }
}
