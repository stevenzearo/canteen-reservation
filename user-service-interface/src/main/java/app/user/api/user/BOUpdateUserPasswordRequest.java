package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class BOUpdateUserPasswordRequest {
    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
