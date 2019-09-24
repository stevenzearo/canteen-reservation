package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

/**
 * @author steve
 */
public class CreateUserRequest {
    @NotNull
    @NotBlank
    @Pattern(value = "\\S+@\\w+\\.com")
    @Property(name = "email")
    public String email;

    @Property(name = "name")
    public String name;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
