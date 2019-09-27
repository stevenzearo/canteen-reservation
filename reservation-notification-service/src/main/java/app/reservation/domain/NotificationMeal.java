package app.reservation.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author steve
 */
@Table(name = "notification_meals")
public class NotificationMeal {
    @NotNull
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "notification_id")
    public Long notificationId;

    @NotNull
    @NotBlank
    @Column(name = "meal_id")
    public String mealId;

    @NotNull
    @NotBlank
    @Column(name = "meal_name")
    public String mealName;

    @NotNull
    @Column(name = "meal_price")
    public Double mealPrice;
}
