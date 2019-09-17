package app.user.api;

import app.user.api.admin.AdminLoginRequest;
import app.user.api.admin.AdminLoginResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author steve
 */
public interface AdminWebService {
    @PUT
    @Path("/login/admin")
    AdminLoginResponse login(AdminLoginRequest request);
}
