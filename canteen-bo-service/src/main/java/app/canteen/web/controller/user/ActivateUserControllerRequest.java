package app.canteen.web.controller.user;

import app.canteen.web.ajax.user.UserStatusAJAXView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class ActivateUserControllerRequest {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @Property(name = "status")
    public UserStatusAJAXView status;
}
