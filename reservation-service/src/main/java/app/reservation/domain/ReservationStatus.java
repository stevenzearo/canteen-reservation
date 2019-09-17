package app.reservation.domain;

import core.framework.db.DBEnumValue;

/**
 * @author steve
 */
public enum ReservationStatus {
    @DBEnumValue("LOCKED")
    LOCKED,
    @DBEnumValue("UNLOCKED")
    UNLOCKED
    }