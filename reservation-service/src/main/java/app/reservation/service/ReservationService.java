package app.reservation.service;

import app.reservation.api.kafka.CancellingReservationMessage;
import app.reservation.api.kafka.SendingEmailReservationMessage;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservationStatusView;
import app.reservation.api.reservation.ReserveRequest;
import app.reservation.api.reservation.ReserveResponse;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.domain.Reservation;
import app.reservation.domain.ReservationMeal;
import app.reservation.domain.ReservationStatus;
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
    MessagePublisher<SendingEmailReservationMessage> reservingPublisher;

    @Inject
    MessagePublisher<CancellingReservationMessage> cancellingPublisher;

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
        SendingEmailReservationMessage message = new SendingEmailReservationMessage();
        message.reservationId = reservation.id;
        message.userId = reservation.userId;
        message.reservationDeadline = request.reservingDeadline;
        reservingPublisher.publish(message);
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

    public GetReservationResponse get(Long userId, String id) {
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
        if (!response.userId.equals(userId)) {
            throw new ConflictException(Strings.format("Miss match with the user id under the reservation, reservation id = {}, user id = {}", id, userId));
        }
        return response;
    }

    public SearchReservationResponse searchByTime(Long userId, SearchReservationRequest request) {
        Query<Reservation> reservationQuery = reservationRepository.select();
        reservationQuery.skip(request.skip);
        reservationQuery.limit(request.limit);
        reservationQuery.where("user_id = ?", userId);
        reservationQuery.where("reserving_time >= ?", request.reservingTimeStart);
        if (request.reservingTimeEnd != null)
            reservationQuery.where("reserving_time <= ?", request.reservingTimeEnd);
        List<Reservation> reservationList = reservationQuery.fetch();
        List<SearchReservationResponse.Reservation> reservationViewList = reservationList.stream()
            .map(reservation -> view(reservation, searchMealIdList(reservation.id))).collect(Collectors.toList());
        SearchReservationResponse response = new SearchReservationResponse();
        response.total = (long) reservationQuery.count();
        response.reservationList = reservationViewList;
        return response;
    }

    public void cancel(Long userId, String id) {
        Reservation reservation = reservationRepository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("Reservation not found, id = {}", id)));
        if (reservation.userId.equals(userId)) {
            CancellingReservationMessage message = new CancellingReservationMessage();
            message.reservationId = reservation.id;
            message.userId = reservation.userId;
            cancellingPublisher.publish(message);
            reservation.status = ReservationStatus.CANCEL;
            reservationRepository.partialUpdate(reservation);
        } else {
            throw new ConflictException(Strings.format("Miss match with the user id under the reservation, user id = {}", userId));
        }
    }

    private SearchReservationResponse.Reservation view(Reservation reservation) {
        SearchReservationResponse.Reservation reservationView = new SearchReservationResponse.Reservation();
        reservationView.id = reservation.id;
        reservationView.amount = reservation.reservingAmount;
        reservationView.reservingTime = reservation.reservingTime;
        reservationView.eatingTime = reservation.eatingTime;
        reservationView.userId = reservation.userId;
        reservationView.restaurantId = reservation.restaurantId;
        reservationView.status = ReservationStatusView.valueOf(reservation.status.name());
        return reservationView;
    }

    private SearchReservationResponse.Reservation view(Reservation reservation, List<String> mealIdList) {
        SearchReservationResponse.Reservation view = view(reservation);
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