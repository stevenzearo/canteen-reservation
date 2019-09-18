package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public enum LoginStatus {
    @Property(name = "SUCCESS")
    SUCCESS,
    @Property(name = "FAILED")
    FAILED
    }
