package app.canteen.web.controller;

import app.canteen.web.controller.user.ActivateUserRequest;
import app.canteen.web.controller.user.CreateUserRequest;
import app.canteen.web.controller.user.RestUserPasswordRequest;
import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOUpdateUserPasswordRequest;
import app.user.api.user.BOUpdateUserStatusRequest;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class UserController {
    @Inject
    BOUserWebService userWebService;

    public Response createUser(Request request) {
        CreateUserRequest controllerRequest = request.bean(CreateUserRequest.class);
        BOCreateUserRequest createUserRequest = new BOCreateUserRequest();
        createUserRequest.name = controllerRequest.name;
        createUserRequest.email = controllerRequest.email;
        createUserRequest.password = controllerRequest.password;
        createUserRequest.status = UserStatusView.valueOf(controllerRequest.status.name());
        return Response.bean(userWebService.create(createUserRequest)); // should return a page, return text for test.
    }

    // use user_id to reset password
    public Response resetPassword(Request request) {
        RestUserPasswordRequest resetPasswordRequest = request.bean(RestUserPasswordRequest.class);
        BOUpdateUserPasswordRequest updateRequest = new BOUpdateUserPasswordRequest();
        updateRequest.password = resetPasswordRequest.password;
        userWebService.updatePassword(resetPasswordRequest.id, updateRequest);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }

    // use user id to change user status
    public Response activate(Request request) {
        ActivateUserRequest activateRequest = request.bean(ActivateUserRequest.class);
        BOUpdateUserStatusRequest updateRequest = new BOUpdateUserStatusRequest();
        updateRequest.status = UserStatusView.valueOf(activateRequest.status.name());
        userWebService.updateStatus(activateRequest.id, updateRequest);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }
}
