package app.canteen.web.controller;

import app.canteen.web.controller.user.ActivateUserControllerRequest;
import app.canteen.web.controller.user.CreateUserControllerRequest;
import app.canteen.web.controller.user.RestUserPasswordControllerRequest;
import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.UpdateUserPasswordRequest;
import app.user.api.user.UpdateUserStatusRequest;
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
        CreateUserControllerRequest controllerRequest = request.bean(CreateUserControllerRequest.class);
        BOCreateUserRequest createUserRequest = new BOCreateUserRequest();
        createUserRequest.name = controllerRequest.name;
        createUserRequest.email = controllerRequest.email;
        createUserRequest.password = controllerRequest.password;
        createUserRequest.status = UserStatusView.valueOf(controllerRequest.status.name());
        return Response.bean(userWebService.create(createUserRequest)); // should return a page, return text for test.
    }

    // use user_id to reset password
    public Response resetPassword(Request request) {
        RestUserPasswordControllerRequest resetPasswordRequest = request.bean(RestUserPasswordControllerRequest.class);
        UpdateUserPasswordRequest updateRequest = new UpdateUserPasswordRequest();
        updateRequest.password = resetPasswordRequest.password;
        userWebService.updatePassword(resetPasswordRequest.id, updateRequest);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }

    // use user id to change user status
    public Response activate(Request request) {
        ActivateUserControllerRequest activateRequest = request.bean(ActivateUserControllerRequest.class);
        UpdateUserStatusRequest updateRequest = new UpdateUserStatusRequest();
        updateRequest.status = UserStatusView.valueOf(activateRequest.status.name());
        userWebService.updateStatus(activateRequest.id, updateRequest);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }
}
