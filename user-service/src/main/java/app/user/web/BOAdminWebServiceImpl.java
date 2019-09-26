package app.user.web;

import app.user.api.BOAdminWebService;
import app.user.api.admin.BOAdminLoginRequest;
import app.user.api.admin.BOAdminLoginResponse;
import app.user.service.BOAdminService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BOAdminWebServiceImpl implements BOAdminWebService {
    @Inject
    BOAdminService service;


    @Override
    public BOAdminLoginResponse login(BOAdminLoginRequest request) {
        return service.login(request);
    }
}
