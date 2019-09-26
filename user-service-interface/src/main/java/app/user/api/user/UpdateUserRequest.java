package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;

/**
 * @author steve
 */
public class UpdateUserRequest {
    @NotBlank
    @Property(name = "name")
    public String name;

    @Property(name = "password")
    public String password;

    @Property(name = "email")
    public String email;
}
