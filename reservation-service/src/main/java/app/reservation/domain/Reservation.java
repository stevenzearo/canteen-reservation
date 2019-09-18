package app.reservation.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
@Table(name = "reservation")
public class Reservation {
    @NotNull
    @PrimaryKey
    @Column(name = "id")
    public String id;

    @NotNull
    @Column(name = "reserve_amount")
    public Double amount;

    @NotNull
    @Column(name = "reserve_time")
    public ZonedDateTime reserveTime;

    @NotNull
    @Column(name = "status")
    public ReservationStatus status;

    @NotNull
    @Column(name = "eat_time")
    public ZonedDateTime eatTime;

    @NotNull
    @Column(name = "user_id")
    public Long userId;

    @NotNull
    @Column(name = "restaurant_id")
    public String restaurantId;
}