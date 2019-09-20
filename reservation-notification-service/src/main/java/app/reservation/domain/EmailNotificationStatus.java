package app.reservation.domain;

import core.framework.db.DBEnumValue;

/**
 * @author steve
 */
public enum EmailNotificationStatus {
    @DBEnumValue("READY")
    READY,
    @DBEnumValue("SENT")
    SENT
}
