package app.reservation.service;

import app.reservation.api.message.ReservationMessage;
import app.reservation.api.reservation.ReservationStatus;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.api.reservation.UpdateReservationRequest;
import app.reservation.domain.Reservation;
import app.reservation.domain.ReservationMeal;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.util.Lists;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class ReservationService {
    @Inject
    MessagePublisher<ReservationMessage> publisher;

    @Inject
    Database database;

    @Inject
    Repository<Reservation> reservationRepository;

    @Inject
    Repository<ReservationMeal> reservationMealRepository;

    public ReservationView reserve(ReserveRequest request) {
        Reservation reservation = new Reservation();
        reservation.id = UUID.randomUUID().toString();
        reservation.amount = request.amount;
        reservation.reserveTime = request.reserveTime;
        reservation.eatTime = request.eatTime;
        reservation.userId = request.userId;
        reservation.restaurantId = request.restaurantId;
        reservation.status = app.reservation.domain.ReservationStatus.OK;
        try (Transaction transaction = database.beginTransaction()) {
            reservationRepository.insert(reservation);
            request.mealIdList.forEach(mealId -> {
                ReservationMeal reservationMeal = new ReservationMeal();
                reservationMeal.mealId = mealId;
                reservationMeal.userReservationId = reservation.id;
                reservationMealRepository.insert(reservationMeal);
            });
            transaction.commit();
        }
        ReservationMessage message = new ReservationMessage();
        message.reservationId = reservation.restaurantId;
        message.userId = reservation.userId;
        message.reservationDeadline = request.reserveDeadline;
        publishMessage(message);
        List<String> mealIdList = request.mealIdList;
        return view(reservation, mealIdList);
    }

    public ReservationView get(String id) {
        return view(reservationRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Reservation not found, id = {}", id))));
    }

    public SearchReservationResponse searchListByConditions(SearchReservationRequest request) {
        Query<Reservation> reservationQuery = reservationRepository.select();
        reservationQuery.skip(request.skip);
        reservationQuery.limit(request.limit);
        if (request.status != null)
            reservationQuery.where("status = ?", app.reservation.domain.ReservationStatus.valueOf(request.status.name()));
        if (request.reserveTimeEqualLaterThan != null) {
            reservationQuery.where("reserve_time >= ?", request.reserveTimeEqualLaterThan);
        } else if (request.reserveTimeEqualBeforeThan != null) {
            reservationQuery.where("reserve_time <= ?", request.reserveTimeEqualBeforeThan);
        } else if (request.reserveTimeEqual != null) {
            reservationQuery.where("reserve_time = ?", request.reserveTimeEqual);
        }
        if (request.eatTimeEqual != null) {
            reservationQuery.where("eat_time = ?", request.eatTimeEqual);
        } else if (request.eatTimeEqualLaterThan != null) {
            reservationQuery.where("eat_time >= ?", request.eatTimeEqualLaterThan);
        } else if (request.eatTimeEqualBeforeThan != null) {
            reservationQuery.where("eat_time <= ?", request.eatTimeEqualBeforeThan);
        }
        if (request.amountEqual != null) {
            reservationQuery.where("amount = ?", request.amountEqual);
        } else if (request.amountEqualGreaterThan != null) {
            reservationQuery.where("amount >= ?", request.amountEqualGreaterThan);
        } else if (request.amountEqualLimitThan != null) {
            reservationQuery.where("amount <= ?", request.amountEqualLimitThan);
        }
        if (request.userId != null)
            reservationQuery.where("user_id = ?", request.userId);
        if (!Strings.isBlank(request.restaurantId))
            reservationQuery.where("restaurant_id = ?", request.restaurantId);
        List<Reservation> reservationList = reservationQuery.fetch();
        List<ReservationView> reservationViewList = reservationList.stream().map(
            reservation -> {
                Query<ReservationMeal> mealQuery = reservationMealRepository.select();
                mealQuery.where("reservation_id = ?", reservation.id);
                List<ReservationMeal> reservationMealList = mealQuery.fetch();
                List<String> mealIdList = Lists.newArrayList();
                reservationMealList.forEach(reservationMeal -> mealIdList.add(reservationMeal.mealId));
                return view(reservation, mealIdList);
            }
        ).collect(Collectors.toList());
        SearchReservationResponse response = new SearchReservationResponse();
        response.total = (long) reservationQuery.count();
        response.reservationViewList = reservationViewList;
        return response;
    }

    public void update(String id, UpdateReservationRequest update) {
        Reservation reservation = reservationRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Reservation not found, id = {}", id)));
        reservation.amount = update.amount;
        reservation.reserveTime = update.reserveTime;
        reservation.restaurantId = update.restaurantId;
        reservation.eatTime = update.eatTime;
        reservation.status = app.reservation.domain.ReservationStatus.valueOf(update.status.name());
        reservation.userId = update.userId;
        reservationRepository.partialUpdate(reservation);
    }

    private void publishMessage(ReservationMessage message) {
        publisher.publish(message);
    }

    private ReservationView view(Reservation reservation) {
        ReservationView reservationView = new ReservationView();
        reservationView.id = reservation.id;
        reservationView.amount = reservation.amount;
        reservationView.reserveTime = reservation.reserveTime;
        reservationView.eatTime = reservation.eatTime;
        reservationView.userId = reservation.userId;
        reservationView.restaurantId = reservation.restaurantId;
        reservationView.status = ReservationStatus.valueOf(reservation.status.name());
        return reservationView;
    }

    private ReservationView view(Reservation reservation, List<String> mealIdList) {
        ReservationView view = view(reservation);
        view.mealIdList = mealIdList;
        return view;
    }
}