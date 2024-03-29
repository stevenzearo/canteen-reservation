package app.canteen.web.ajax.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author steve
 */
public class ReservingReservationAJAXRequest {
    @NotNull
    @Property(name = "eating_time")
    public ZonedDateTime eatingTime;

    @NotNull
    @NotBlank
    @Property(name = "restaurant_id")
    public String restaurantId;

    @NotNull
    @Property(name = "meal_id_list")
    public List<String> mealIdList;
}
