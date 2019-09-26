package app.user;

import app.user.api.BOAdminWebService;
import app.user.api.BOUserWebService;
import app.user.api.UserWebService;
import app.user.domain.Admin;
import app.user.domain.User;
import app.user.service.BOAdminService;
import app.user.service.UserService;
import app.user.web.BOAdminWebServiceImpl;
import app.user.web.BOUserWebServiceImpl;
import app.user.web.UserWebServiceImpl;
import core.framework.module.Module;

/**
 * @author steve
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        db().repository(User.class);
        db().repository(Admin.class);
        bind(UserService.class);
        bind(BOAdminService.class);
        api().service(UserWebService.class, bind(UserWebServiceImpl.class));
        api().service(BOUserWebService.class, bind(BOUserWebServiceImpl.class));
        api().service(BOAdminWebService.class, bind(BOAdminWebServiceImpl.class));
    }
}
