package app.user.web;

import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.LoginRequest;
import app.user.api.user.LoginResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserView;
import app.user.service.UserService;
import core.framework.inject.Inject;

import java.util.Optional;

/**
 * @author steve
 */
public class UserWebServiceImpl implements UserWebService {
    @Inject
    UserService service;

    @Override
    public UserView create(CreateUserRequest request) {
        return service.create(request);
    }

    @Override
    public SearchUserResponse searchByPage(SearchUserRequest request) {
        return service.searchByPage(request);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return service.login(request);
    }

    @Override
    public void update(Long id, UpdateUserRequest request) {
        service.update(id, request);
    }
}
