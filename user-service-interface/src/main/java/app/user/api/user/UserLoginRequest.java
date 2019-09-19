package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class UserLoginRequest {
    @NotNull
    @Property(name = "email")
    public String email;

    @NotNull
    @Property(name = "password")
    public String password;
}
