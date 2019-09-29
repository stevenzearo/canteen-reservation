package app.reservation.service;

import app.reservation.api.reservation.BOGetReservationResponse;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import app.reservation.api.reservation.ReservationStatusView;
import app.reservation.domain.Reservation;
import app.reservation.domain.ReservationMeal;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.util.List;

/**
 * @author steve
 */
public class BOReservationService {
    @Inject
    Repository<Reservation> reservationRepository;

    @Inject
    Repository<ReservationMeal> reservationMealRepository;


    public BOGetReservationResponse get(String id) {
        Reservation reservation = reservationRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Reservation not found, id = {}", id)));
        List<ReservationMeal> reservationMeals = reservationMealRepository.select("reservation_id = ?", id);
        BOGetReservationResponse response = new BOGetReservationResponse();
        response.id = reservation.id;
        response.userId = reservation.userId;
        response.userName = reservation.userName;
        response.restaurantId = reservation.restaurantId;
        response.restaurantName = reservation.restaurantName;
        response.reservingTime = reservation.reservingTime;
        response.status = ReservationStatusView.valueOf(reservation.status.name());
        response.meals = Lists.newArrayList();
        reservationMeals.forEach(reservationMeal -> {
            BOGetReservationResponse.Meal mealView = new BOGetReservationResponse.Meal();
            mealView.id = reservationMeal.mealId;
            mealView.name = reservationMeal.mealName;
            mealView.price = reservationMeal.mealPrice;
            response.meals.add(mealView);
        });
        return response;
    }

    public BOSearchReservationResponse search(BOSearchReservationRequest request) {
        Query<Reservation> query = reservationRepository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        if (!Strings.isBlank(request.userName)) {
            query.where("user_name like ?", request.userName);
        }
        if (!Strings.isBlank(request.restaurantName)) {
            query.where("restaurant_name like ?", request.restaurantName);
        }
        if (request.reservingTimeStart != null) {
            query.where("reserving_time >= ?", request.reservingTimeStart);
        }
        if (request.reservingTimeEnd != null) {
            query.where("reserving_time <= ?", request.reservingTimeEnd);
        }
        BOSearchReservationResponse response = new BOSearchReservationResponse();
        response.total = (long) query.count();
        response.reservations = Lists.newArrayList();
        List<Reservation> reservations = query.fetch();
        reservations.forEach(reservation -> {
            List<ReservationMeal> meals = searchMeals(reservation.id);
            BOSearchReservationResponse.Reservation reservationView = view(reservation, meals);
            response.reservations.add(reservationView);
        });
        return response;
    }

    private BOSearchReservationResponse.Reservation view(Reservation reservation, List<ReservationMeal> meals) {
        BOSearchReservationResponse.Reservation reservationView = new BOSearchReservationResponse.Reservation();
        reservationView.id = reservation.id;
        reservationView.amount = reservation.amount;
        reservationView.reservingTime = reservation.reservingTime;
        reservationView.eatingTime = reservation.eatingTime;
        reservationView.userId = reservation.userId;
        reservationView.userName = reservation.userName;
        reservationView.restaurantId = reservation.restaurantId;
        reservationView.restaurantName = reservation.restaurantName;
        reservationView.status = ReservationStatusView.valueOf(reservation.status.name());
        reservationView.meals = Lists.newArrayList();
        meals.forEach(reservationMeal -> {
            BOSearchReservationResponse.Meal mealView = new BOSearchReservationResponse.Meal();
            mealView.id = reservationMeal.mealId;
            mealView.name = reservationMeal.mealName;
            mealView.price = reservationMeal.mealPrice;
            reservationView.meals.add(mealView);
        });
        return reservationView;
    }

    private List<ReservationMeal> searchMeals(String reservationId) {
        Query<ReservationMeal> mealQuery = reservationMealRepository.select();
        mealQuery.where("reservation_id = ?", reservationId);
        return mealQuery.fetch();
    }
}