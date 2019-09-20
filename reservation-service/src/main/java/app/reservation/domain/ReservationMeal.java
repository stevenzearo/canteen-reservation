package app.reservation.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author steve
 */
@Table(name = "reservation_meals")
public class ReservationMeal {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "reservation_id")
    public String userReservationId;

    @NotNull
    @Column(name = "meal_id")
    public String mealId;
}