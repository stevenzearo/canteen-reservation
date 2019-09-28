package app.reservation.service;

import app.reservation.api.kafka.CancellingReservationMessage;
import app.reservation.api.kafka.SendingEmailReservationMessage;
import app.reservation.api.reservation.GetReservationResponse;
import app.reservation.api.reservation.ReservationStatusView;
import app.reservation.api.reservation.ReserveResponse;
import app.reservation.api.reservation.ReservingRequest;
import app.reservation.api.reservation.SearchReservationRequest;
import app.reservation.api.reservation.SearchReservationResponse;
import app.reservation.domain.Reservation;
import app.reservation.domain.ReservationMeal;
import app.reservation.domain.ReservationRestaurant;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.OptionalLong;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class ReservationService {
    private final Logger logger = LoggerFactory.getLogger(ReservationService.class);

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

    @Inject
    Repository<ReservationRestaurant> reservationRestaurantRepository;

    private void batchCreateMeal(String reservationId, String restaurantId, List<ReservingRequest.Meal> mealList) {
        List<ReservationMeal> reservationMealList = Lists.newArrayList();
        mealList.forEach(meal -> {
            ReservationMeal reservationMeal = new ReservationMeal();
            reservationMeal.mealId = meal.id;
            reservationMeal.reservationId = reservationId;
            reservationMeal.restaurantId = restaurantId;
            reservationMeal.mealName = meal.name;
            reservationMeal.mealPrice = meal.price;
            OptionalLong reservationMealId = reservationMealRepository.insert(reservationMeal);
            if (reservationMealId.isPresent()) reservationMeal.id = reservationMealId.getAsLong();
            reservationMealList.add(reservationMeal);
        });
    }

    private void createRestaurant(String reservationId, ReservingRequest request) {
        ReservationRestaurant reservationRestaurant = new ReservationRestaurant();
        reservationRestaurant.reservationId = reservationId;
        reservationRestaurant.restaurantId = request.restaurant.id;
        reservationRestaurant.restaurantName = request.restaurant.name;
        reservationRestaurant.restaurantPhone = request.restaurant.phone;
        reservationRestaurant.restaurantAddress = request.restaurant.address;
        OptionalLong reservationRestaurantId = reservationRestaurantRepository.insert(reservationRestaurant);
        if (reservationRestaurantId.isPresent()) reservationRestaurant.id = reservationRestaurantId.getAsLong();
    }

    private Reservation createReservation(Long userId, ReservingRequest request) {
        Reservation reservation = new Reservation();
        reservation.id = UUID.randomUUID().toString();
        reservation.reservingAmount = request.amount;
        reservation.reservingTime = ZonedDateTime.now();
        reservation.eatingTime = request.eatingTime;
        reservation.userId = userId;
        reservation.restaurantId = request.restaurant.id;
        reservation.status = ReservationStatus.OK;
        reservationRepository.insert(reservation);
        return reservation;
    }

    public ReserveResponse reserve(Long userId, ReservingRequest request) {
        Reservation reservation;
        try (Transaction transaction = database.beginTransaction()) {
            reservation = createReservation(userId, request);
            createRestaurant(reservation.id, request);
            batchCreateMeal(reservation.id, request.restaurant.id, request.mealList);
            transaction.commit();
        }
        SendingEmailReservationMessage message = new SendingEmailReservationMessage();
        message.reservationId = reservation.id;
        message.restaurantId = reservation.restaurantId;
        message.userId = reservation.userId;
        message.reservationDeadline = request.restaurant.reservingDeadline;
        message.mealIdList = request.mealList.stream().map(meal -> meal.id).collect(Collectors.toList());
        logger.warn(Strings.format("according reservation id = {}, sending message", reservation.id));
        reservingPublisher.publish(message);
        ReserveResponse response = new ReserveResponse();
        response.id = reservation.id;
        response.amount = reservation.reservingAmount;
        response.reservingTime = reservation.reservingTime;
        response.eatingTime = reservation.eatingTime;
        response.userId = reservation.userId;
        response.restaurantId = reservation.restaurantId;
        response.status = ReservationStatusView.valueOf(reservation.status.name());
        response.mealIdList = request.mealList.stream().map(meal -> meal.id).collect(Collectors.toList());
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