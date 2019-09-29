package app.canteen.web.ajax.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class ActivateUserAJAXRequest {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @Property(name = "status")
    public UserStatusAJAXView status;
}
