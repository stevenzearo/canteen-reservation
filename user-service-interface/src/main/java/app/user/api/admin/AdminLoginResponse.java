package app.user.api.admin;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class AdminLoginResponse {
    @Property(name = "status")
    public LoginStatus status;
}
