package app.user.domain;

import core.framework.db.DBEnumValue;

/**
 * @author steve
 */
public enum UserStatus {
    @DBEnumValue("VALID")
    VALID,
    @DBEnumValue("INVALID")
    INVALID
}
