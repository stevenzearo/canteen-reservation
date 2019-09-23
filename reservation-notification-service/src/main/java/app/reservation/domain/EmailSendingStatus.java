package app.reservation.domain;

import core.framework.db.DBEnumValue;

/**
 * @author steve
 */
public enum EmailSendingStatus {
    @DBEnumValue("READY")
    READY,
    @DBEnumValue("SENT")
    SENT,
    @DBEnumValue("CANCEL")
    CANCEL

}
