package app.user.web;

import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.CreateUserResponse;
import app.user.api.user.GetUserResponse;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserLoginResponse;
import app.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class UserWebServiceImpl implements UserWebService {
    @Inject
    UserService service;

    @Override
    public CreateUserResponse create(CreateUserRequest request) {
        return service.create(request);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        return service.login(request);
    }

    @Override
    public GetUserResponse get(Long id) {
        return service.get(id);
    }
}
