package app.reservation.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author steve
 */
@Table(name = "reservation_meal")
public class ReservationMeal {
    @NotNull
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "user_reservation_id")
    public String userReservationId;

    @NotNull
    @Column(name = "meal_id")
    public Long mealId;
}
