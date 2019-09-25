package app.reservation.service;

import app.reservation.api.kafka.MessageStatus;
import app.reservation.api.kafka.SendEmailReservationMessage;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservationStatusView;
import app.reservation.api.reservation.ReservationView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.ReserveResponse;
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
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class ReservationService {
    @Inject
    MessagePublisher<SendEmailReservationMessage> publisher;

    @Inject
    Database database;

    @Inject
    Repository<Reservation> reservationRepository;

    @Inject
    Repository<ReservationMeal> reservationMealRepository;

    public ReserveResponse reserve(Long userId, ReserveRequest request) {
        Reservation reservation = new Reservation();
        reservation.id = UUID.randomUUID().toString();
        reservation.reservingAmount = request.amount;
        reservation.reservingTime = request.reservingTime;
        reservation.eatingTime = request.eatingTime;
        reservation.userId = userId;
        reservation.restaurantId = request.restaurantId;
        reservation.status = app.reservation.domain.ReservationStatus.OK;
        try (Transaction transaction = database.beginTransaction()) {
            reservationRepository.insert(reservation);
            request.mealIdList.forEach(mealId -> {
                ReservationMeal reservationMeal = new ReservationMeal();
                reservationMeal.mealId = mealId;
                reservationMeal.reservationId = reservation.id;
                reservationMealRepository.insert(reservationMeal);
            });
            transaction.commit();
        }
        SendEmailReservationMessage message = new SendEmailReservationMessage();
        message.reservationId = reservation.id;
        message.userId = reservation.userId;
        message.reservationDeadline = request.reservingDeadline;
        message.status = MessageStatus.CREATE;
        publishMessage(message);
        List<String> mealIdList = request.mealIdList;
        ReserveResponse response = new ReserveResponse();
        response.id = reservation.id;
        response.amount = reservation.reservingAmount;
        response.reservingTime = reservation.reservingTime;
        response.eatingTime = reservation.eatingTime;
        response.userId = reservation.userId;
        response.restaurantId = reservation.restaurantId;
        response.status = ReservationStatusView.valueOf(reservation.status.name());
        response.mealIdList = mealIdList;
        return response;
    }

    public GetReservationResponse get(String id) {
        Reservation reservation = reservationRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Reservation not found, id = {}", id)));
        GetReservationResponse response = new GetReservationResponse();
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

    public GetReservationResponse get(Long userId, String id) {
        GetReservationResponse response = get(id);
        if (!response.userId.equals(userId)) {
            throw new ConflictException(Strings.format("Miss match with the user id under the notification, user id = {}", userId));
        }
        return response;
    }

    public SearchReservationResponse search(SearchReservationRequest request) {
        Query<Reservation> reservationQuery = reservationRepository.select();
        reservationQuery.skip(request.skip);
        reservationQuery.limit(request.limit);
        if (request.userId != null)
            reservationQuery.where("user_id = ?", request.userId);
        if (request.status != null)
            reservationQuery.where("status = ?", app.reservation.domain.ReservationStatus.valueOf(request.status.name()));
        if (request.reservingTimeStart != null)
            reservationQuery.where("reserving_time >= ?", request.reservingTimeStart);
        if (request.reservingTimeEnd != null)
            reservationQuery.where("reserving_time <= ?", request.reservingTimeEnd);
        if (request.amountEqual != null) {
            reservationQuery.where("amount = ?", request.amountEqual);
        } else if (request.amountEqualGreaterThan != null) {
            reservationQuery.where("amount >= ?", request.amountEqualGreaterThan);
        } else if (request.amountEqualLimitThan != null) {
            reservationQuery.where("amount <= ?", request.amountEqualLimitThan);
        }
        if (!Strings.isBlank(request.restaurantId))
            reservationQuery.where("restaurant_id = ?", request.restaurantId);
        List<Reservation> reservationList = reservationQuery.fetch();
        List<ReservationView> reservationViewList = reservationList.stream()
            .map(reservation -> view(reservation, searchMealIdList(reservation.id))).collect(Collectors.toList());
        SearchReservationResponse response = new SearchReservationResponse();
        response.total = (long) reservationQuery.count();
        response.reservationList = reservationViewList;
        return response;
    }

    public void update(String id, UpdateReservationRequest update) {
        Reservation reservation = reservationRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Reservation not found, id = {}", id)));
        if (update.status == ReservationStatusView.CANCEL) {
            SendEmailReservationMessage message = new SendEmailReservationMessage();
            message.reservationId = reservation.id;
            message.userId = reservation.userId;
            message.status = MessageStatus.CANCEL;
            publishMessage(message);
        }
        reservation.reservingAmount = update.amount;
        reservation.reservingTime = update.reservingTime;
        reservation.eatingTime = update.eatingTime;
        reservation.restaurantId = update.restaurantId;
        reservation.status = app.reservation.domain.ReservationStatus.valueOf(update.status.name());
        reservation.userId = update.userId;

        reservationRepository.partialUpdate(reservation);
    }

    private void publishMessage(SendEmailReservationMessage message) {
        publisher.publish(message);
    }

    private ReservationView view(Reservation reservation) {
        ReservationView reservationView = new ReservationView();
        reservationView.id = reservation.id;
        reservationView.amount = reservation.reservingAmount;
        reservationView.reservingTime = reservation.reservingTime;
        reservationView.eatingTime = reservation.eatingTime;
        reservationView.userId = reservation.userId;
        reservationView.restaurantId = reservation.restaurantId;
        reservationView.status = ReservationStatusView.valueOf(reservation.status.name());
        return reservationView;
    }

    private ReservationView view(Reservation reservation, List<String> mealIdList) {
        ReservationView view = view(reservation);
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