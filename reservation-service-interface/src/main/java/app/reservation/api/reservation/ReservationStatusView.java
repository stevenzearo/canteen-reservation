package app.reservation.api.reservation;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public enum ReservationStatusView {
    @Property(name = "OK")
    OK,
    @Property(name = "CANCEL")
    CANCEL
}
