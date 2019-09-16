package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class UserView {
    @Property(name = "id")
    public Long id;

    @Property(name = "email")
    public String email;

    @Property(name = "name")
    public String name;

    @Property(name = "password")
    public String password;

    @Property(name = "status")
    public UserStatus status;
}
