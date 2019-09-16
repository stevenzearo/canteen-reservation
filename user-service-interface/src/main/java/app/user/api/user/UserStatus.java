package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public enum UserStatus {
    @Property(name = "VALID")
    VALID,
    @Property(name = "INVALID")
    INVALID
}
