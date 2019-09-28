package app.reservation.service;

import app.reservation.api.reservation.BOGetReservationResponse;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import app.reservation.api.reservation.ReservationStatusView;
import app.reservation.domain.Reservation;
import app.reservation.domain.ReservationMeal;
import app.reservation.domain.ReservationRestaurant;
import app.reservation.domain.ReservationStatus;
import app.reservation.domain.ReservationUser;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * @author steve
 */
public class BOReservationService {
    @Inject
    Repository<Reservation> reservationRepository;

    @Inject
    Repository<ReservationMeal> reservationMealRepository;

    @Inject
    Repository<ReservationRestaurant> reservationRestaurantRepository;

    @Inject
    Repository<ReservationUser> reservationUserRepository;

    @Inject
    Database database;

    public BOGetReservationResponse get(String id) {
        Reservation reservation = reservationRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Reservation not found, id = {}", id)));
        Query<ReservationUser> userQuery = reservationUserRepository.select();
        userQuery.where("reservation_id = ?", id);
        ReservationUser reservationUser = userQuery.fetchOne().orElseThrow(() -> new NotFoundException(Strings.format("reservation user not found, reservation id = {}", id)));
        Query<ReservationRestaurant> restaurantQuery = reservationRestaurantRepository.select();
        restaurantQuery.where("reservation_id = ?", id);
        restaurantQuery.fetchOne().orElseThrow(() -> new NotFoundException(Strings.format("reservation restaurant not found, reservation id = {}", id)));
        List<ReservationMeal> reservationMealList = reservationMealRepository.select("reservation_id = ?", id);

        BOGetReservationResponse response = new BOGetReservationResponse();
        response.id = reservation.id;
        response.amount = reservation.reservingAmount;
        response.reservingTime = reservation.reservingTime;
        response.eatingTime = reservation.eatingTime;
        response.userId = reservation.userId;
        response.restaurantId = reservation.restaurantId;
        response.status = ReservationStatusView.valueOf(reservation.status.name());
        response.mealIdList = searchMealIdList(reservation.id);
        return response;
    }

    public BOSearchReservationResponse search(BOSearchReservationRequest request) {
        Query<Reservation> reservationQuery = reservationRepository.select();
        reservationQuery.skip(request.skip);
        reservationQuery.limit(request.limit);
        List<String> conditions = Lists.newArrayList();
        List<Object> params = Lists.newArrayList();
        if (!Strings.isBlank(request.userName)) {
            conditions.add("user_name like ?");
            params.add(request.userName);
        }
        if (!Strings.isBlank(request.restaurantName)) {
            conditions.add("restaurant_name like ?");
            params.add(request.restaurantName);
        }
        if (request.status != null) {
            conditions.add("reservations.status = ?");
            params.add(ReservationStatus.valueOf(request.status.name()));
        }
        if (request.reservingTimeStart != null) {
            conditions.add("reservations.reserving_time >= ?");
            params.add(request.reservingTimeStart);
        }
        if (request.reservingTimeEnd != null) {
            conditions.add("reservations.reserving_time <= ?");
            params.add(request.reservingTimeEnd);
        }
        StringBuilder sqlCondition = new StringBuilder();
        conditions.stream().forEach(condition -> {
            sqlCondition.append(condition);
            sqlCondition.append(" and ");
        });
        BOSearchReservationResponse response = new BOSearchReservationResponse();
        return response;
    }


    private BOSearchReservationResponse.Reservation view(Reservation reservation) {
        BOSearchReservationResponse.Reservation reservationView = new BOSearchReservationResponse.Reservation();
        reservationView.id = reservation.id;
        reservationView.amount = reservation.reservingAmount;
        reservationView.reservingTime = reservation.reservingTime;
        reservationView.eatingTime = reservation.eatingTime;
        reservationView.userId = reservation.userId;
        reservationView.restaurantId = reservation.restaurantId;
        reservationView.status = ReservationStatusView.valueOf(reservation.status.name());
        return reservationView;
    }

    private BOSearchReservationResponse.Reservation view(Reservation reservation, List<String> mealIdList) {
        BOSearchReservationResponse.Reservation view = view(reservation);
        view.mealIdList = mealIdList;
        return view;
    }

    private List<String> searchMealIdList(String reservationId) {
        List<String> mealIdList = Lists.newArrayList();
        Query<ReservationMeal> mealQuery = reservationMealRepository.select();
        mealQuery.where("reservation_id = ?", reservationId);
        List<ReservationMeal> reservationMealList = mealQuery.fetch();
        reservationMealList.forEach(reservationMeal -> mealIdList.add(reservationMeal.mealId));
        return mealIdList;
    }
}