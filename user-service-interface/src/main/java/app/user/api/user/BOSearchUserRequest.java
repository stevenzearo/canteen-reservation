package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class BOSearchUserRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "name")
    public String name;

    @Property(name = "email")
    public String email;

    @Property(name = "status")
    public UserStatusView status;
}
