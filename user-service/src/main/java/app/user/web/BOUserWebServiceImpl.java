package app.user.web;

import app.user.api.BOUserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.CreateUserResponse;
import app.user.api.user.GetUserResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserView;
import app.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BOUserWebServiceImpl implements BOUserWebService {
    @Inject
    UserService service;

    @Override
    public CreateUserResponse create(CreateUserRequest request) {
        return service.create(request);
    }

    @Override
    public SearchUserResponse search(SearchUserRequest request) {
        return service.searchListByConditions(request);
    }

    @Override
    public void update(Long id, UpdateUserRequest request) {
        service.update(id, request);
    }

    @Override
    public GetUserResponse get(Long id) {
        return service.get(id);
    }
}
