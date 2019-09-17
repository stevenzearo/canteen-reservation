package app.reservation.api.reservation;

import core.framework.api.json.Property;
import core.framework.db.DBEnumValue;

/**
 * @author steve
 */
public enum ReservationStatus {
    @Property(name = "LOCKED")
    LOCKED,
    @Property(name = "UNLOCKED")
    UNLOCKED
}
