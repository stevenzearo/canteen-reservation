package app.user.web;

import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserView;
import app.user.service.UserService;
import core.framework.inject.Inject;

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
    public SearchResponse searchByConditions(SearchUserRequest request) {
        return service.searchByConditions(request);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        return service.login(request);
    }

    @Override
    public void update(Long id, UpdateUserRequest request) {
        service.update(id, request);
    }

    @Override
    public UserView getEmail(String id) {
        return service.getEmail(id);
    }
}
