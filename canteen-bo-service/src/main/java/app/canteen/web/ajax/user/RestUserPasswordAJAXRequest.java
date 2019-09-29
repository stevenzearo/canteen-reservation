package app.canteen.web.ajax.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class RestUserPasswordAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
