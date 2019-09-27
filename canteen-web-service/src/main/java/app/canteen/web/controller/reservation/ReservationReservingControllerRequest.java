package app.canteen.web.controller.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class ReservationReservingControllerRequest {
    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @Property(name = "amount")
    public Double amount;

    @NotNull
    @Property(name = "eating_time")
    public ZonedDateTime eatingTime;

    @NotNull
    @Property(name = "reserving_deadline")
    public ZonedDateTime reservingDeadline;

    @NotNull
    @NotBlank
    @Property(name = "restaurant_id")
    public String restaurantId;

    @NotNull
    @Property(name = "meal_id_list")
    public List<String> mealIdList;
}
