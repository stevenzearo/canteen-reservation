package app.reservation.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author steve
 */
@Table(name = "reservation_restaurants")
public class ReservationRestaurant {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Column(name = "reservation_id")
    public String reservationId;

    @NotNull
    @NotBlank
    @Column(name = "restaurant_id")
    public String restaurantId;

    @NotNull
    @NotBlank
    @Column(name = "restaurant_name")
    public String restaurantName;

    @NotNull
    @NotBlank
    @Column(name = "restaurant_phone")
    public String restaurantPhone;

    @NotNull
    @NotBlank
    @Column(name = "restaurant_address")
    public String restaurantAddress;
}
