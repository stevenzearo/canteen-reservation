package app.user.api.user;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class BOSearchUserResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "users")
    public List<User> users;

    public static class User {
        @Property(name = "id")
        public Long id;

        @Property(name = "email")
        public String email;

        @Property(name = "name")
        public String name;

        @Property(name = "status")
        public UserStatusView status;
    }
}
