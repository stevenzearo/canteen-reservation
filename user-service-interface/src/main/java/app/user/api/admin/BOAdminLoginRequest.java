package app.user.api.admin;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class BOAdminLoginRequest {
    @NotNull
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "password")
    public String password;
}
