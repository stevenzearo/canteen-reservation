package app.user.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class BOUpdateUserStatusRequest {
    @NotNull
    @Property(name = "status")
    public UserStatusView status;
}
