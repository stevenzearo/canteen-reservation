package app.reservation.api.reservation;

import core.framework.api.json.Property;
import core.framework.db.DBEnumValue;

/**
 * @author steve
 */
public enum ReservationStatus {
    @Property(name = "OK")
    OK,
    @Property(name = "CANCEL")
    CANCEL
}
