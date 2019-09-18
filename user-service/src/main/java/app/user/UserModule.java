package app.user;

import app.user.api.AdminWebService;
import app.user.api.UserWebService;
import app.user.api.admin.AdminLoginRequest;
import app.user.api.user.LoginStatus;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserStatus;
import app.user.api.user.UserView;
import app.user.domain.Admin;
import app.user.domain.User;
import app.user.service.AdminService;
import app.user.service.UserService;
import app.user.web.AdminWebServiceImpl;
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
        http().bean(CreateUserRequest.class);
        http().bean(SearchUserRequest.class);
        http().bean(SearchResponse.class);
        http().bean(UpdateUserRequest.class);
        http().bean(UserLoginRequest.class);
        http().bean(UserLoginResponse.class);
        http().bean(UserView.class);
        http().bean(UserStatus.class);
        http().bean(LoginStatus.class);
        http().bean(AdminLoginRequest.class);
        bind(UserService.class);
        bind(AdminService.class);
        api().service(UserWebService.class, bind(UserWebServiceImpl.class));
        api().service(AdminWebService.class, bind(AdminWebServiceImpl.class));
    }
}
