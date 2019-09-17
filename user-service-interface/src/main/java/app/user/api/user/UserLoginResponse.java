package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class UserLoginResponse {
    @Property(name = "status")
    public Boolean status;

    @Property(name = "user")
    public UserView userView;
}
