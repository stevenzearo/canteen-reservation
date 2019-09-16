package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class LoginRequest {
    @Property(name = "email")
    public String email;

    @Property(name = "password")
    public String password;
}
