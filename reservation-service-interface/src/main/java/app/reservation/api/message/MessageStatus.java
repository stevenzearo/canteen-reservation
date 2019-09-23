package app.reservation.api.message;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public enum MessageStatus {
    @Property(name = "CREATE")
    CREATE,
    @Property(name = "CANCEL")
    CANCEL
}
