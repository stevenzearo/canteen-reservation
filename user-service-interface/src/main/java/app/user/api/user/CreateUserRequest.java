package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class CreateUserRequest {
    @NotNull
    @NotBlank
    @Property(name = "email")
    public String email;

    @Property(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
