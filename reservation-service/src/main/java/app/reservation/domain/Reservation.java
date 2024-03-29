package app.reservation.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
@Table(name = "reservations")
public class Reservation {
    @NotNull
    @PrimaryKey
    @Column(name = "id")
    public String id;

    @NotNull
    @Column(name = "amount")
    public Double amount;

    @NotNull
    @Column(name = "reserving_time")
    public ZonedDateTime reservingTime;

    @NotNull
    @Column(name = "eating_time")
    public ZonedDateTime eatingTime;

    @NotNull
    @Column(name = "status")
    public ReservationStatus status;

    @NotNull
    @Column(name = "user_id")
    public Long userId;

    @NotNull
    @Column(name = "user_name")
    public String userName;


    @NotNull
    @NotBlank
    @Column(name = "restaurant_id")
    public String restaurantId;

    @NotNull
    @NotBlank
    @Column(name = "restaurant_name")
    public String restaurantName;
}