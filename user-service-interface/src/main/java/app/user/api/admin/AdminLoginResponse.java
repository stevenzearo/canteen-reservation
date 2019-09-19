package app.user.api.admin;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public class AdminLoginResponse {
    @Property(name = "id")
    public Long id;

    @Property(name = "name")
    public String name;
}
