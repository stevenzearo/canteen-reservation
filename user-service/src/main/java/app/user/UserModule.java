package app.user;

import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.LoginRequest;
import app.user.api.user.LoginResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserStatus;
import app.user.api.user.UserView;
import app.user.domain.User;
import app.user.service.UserService;
import app.user.web.UserWebServiceImpl;
import core.framework.module.Module;

/**
 * @author steve
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        db().repository(User.class);
        http().bean(CreateUserRequest.class);
        http().bean(SearchUserRequest.class);
        http().bean(SearchUserResponse.class);
        http().bean(UpdateUserRequest.class);
        http().bean(LoginRequest.class);
        http().bean(LoginResponse.class);
        http().bean(UserView.class);
        http().bean(UserStatus.class);
        bind(UserService.class);
        api().service(UserWebService.class, bind(UserWebServiceImpl.class));
    }
}
