package app.reservation.api.kafka;

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
