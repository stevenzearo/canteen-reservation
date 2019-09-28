package app.user.web;

import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserPasswordRequest;
import app.user.api.user.BOUpdateUserStatusRequest;
import app.user.service.BOUserService;
import core.framework.inject.Inject;

/**
 * @author steve
 */
public class BOUserWebServiceImpl implements BOUserWebService {
    @Inject
    BOUserService service;

    @Override
    public BOCreateUserResponse create(BOCreateUserRequest request) {
        return service.create(request);
    }

    @Override
    public BOGetUserResponse get(Long id) {
        return service.get(id);
    }

    @Override
    public BOSearchUserResponse search(BOSearchUserRequest request) {
        return service.search(request);
    }

    @Override
    public void updatePassword(Long id, BOUpdateUserPasswordRequest request) {
        service.updatePassword(id, request);
    }

    @Override
    public void updateStatus(Long id, BOUpdateUserStatusRequest request) {
        service.updateStatus(id, request);
    }
}
