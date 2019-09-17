package app.user.api.admin;

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
