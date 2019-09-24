package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class UpdateUserRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "password")
    public String password;

    @Property(name = "email")
    public String email;

    @Property(name = "status")
    public UserStatusView status;
}
