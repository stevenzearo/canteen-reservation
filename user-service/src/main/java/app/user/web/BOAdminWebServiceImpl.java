package app.user.web;

import app.user.api.BOAdminWebService;
import app.user.api.admin.AdminLoginRequest;
import app.user.api.admin.AdminLoginResponse;
import app.user.service.AdminService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BOAdminWebServiceImpl implements BOAdminWebService {
    @Inject
    AdminService service;


    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        return service.login(request);
    }
}
