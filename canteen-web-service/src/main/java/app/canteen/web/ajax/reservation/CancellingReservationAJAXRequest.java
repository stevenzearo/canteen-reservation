package app.canteen.web.ajax.reservation;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class CancellingReservationAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "reservation_id")
    public String reservationId;

    @NotNull
    @NotBlank
    @Property(name = "restaurant_id")
    public String restaurantId;
}
