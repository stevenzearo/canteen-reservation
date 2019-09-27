package app.canteen.web.controller.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class CancellingControllerRequest {
    @NotNull
    @Property(name = "user_id")
    public Long userId;

    @NotNull
    @NotBlank
    @Property(name = "reservation_id")
    public String reservationId;

    @NotNull
    @NotBlank
    @Property(name = "restaurant_id")
    public String restaurantId;
}
