package app.reservation.service;

import app.reservation.api.reservation.BOGetReservationResponse;
import app.reservation.api.reservation.BOSearchReservationRequest;
import app.reservation.api.reservation.BOSearchReservationResponse;
import app.reservation.api.reservation.ReservationStatusView;
import app.reservation.domain.Reservation;
import app.reservation.domain.ReservationMeal;
import app.reservation.domain.ReservationRestaurant;
import app.reservation.domain.ReservationStatus;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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
        if (request.userId != null)
            reservationQuery.where("user_id = ?", request.userId);
        if (request.status != null)
            reservationQuery.where("status = ?", ReservationStatus.valueOf(request.status.name()));
        if (request.reservingTimeStart != null)
            reservationQuery.where("reserving_time >= ?", request.reservingTimeStart);
        if (request.reservingTimeEnd != null)
            reservationQuery.where("reserving_time <= ?", request.reservingTimeEnd);
        if (!Strings.isBlank(request.restaurantId))
            reservationQuery.where("restaurant_id = ?", request.restaurantId);
        List<Reservation> reservationList = reservationQuery.fetch();
        List<BOSearchReservationResponse.Reservation> reservationViewList = reservationList.stream()
            .map(reservation -> view(reservation, searchMealIdList(reservation.id))).collect(Collectors.toList());
        BOSearchReservationResponse response = new BOSearchReservationResponse();
        response.total = (long) reservationQuery.count();
        response.reservationList = reservationViewList;
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