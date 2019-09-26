package app.user.api;

import app.user.api.admin.BOAdminLoginRequest;
import app.user.api.admin.BOAdminLoginResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author steve
 */
public interface BOAdminWebService {
    @PUT
    @Path("/bo/admin/login")
    BOAdminLoginResponse login(BOAdminLoginRequest request);
}
