package app.reservation.domain;

import core.framework.db.DBEnumValue;

/**
 * @author steve
 */
public enum ReservationStatus {
    @DBEnumValue("OK")
    OK,
    @DBEnumValue("CANCEL")
    CANCEL
}