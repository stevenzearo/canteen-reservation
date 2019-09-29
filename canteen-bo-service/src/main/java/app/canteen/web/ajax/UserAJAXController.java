package app.canteen.web.ajax;

import app.canteen.web.ajax.user.ActivateUserAJAXRequest;
import app.canteen.web.ajax.user.CreateUserAJAXRequest;
import app.canteen.web.ajax.user.RestUserPasswordAJAXRequest;
import app.canteen.web.ajax.user.SearchUserAJAXRequest;
import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserPasswordRequest;
import app.user.api.user.BOUpdateUserStatusRequest;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class UserAJAXController {
    @Inject
    BOUserWebService service;

    @Inject
    BOUserWebService userWebService;

    public Response createUser(Request request) {
        CreateUserAJAXRequest controllerRequest = request.bean(CreateUserAJAXRequest.class);
        BOCreateUserRequest createUserRequest = new BOCreateUserRequest();
        createUserRequest.name = controllerRequest.name;
        createUserRequest.email = controllerRequest.email;
        createUserRequest.password = controllerRequest.password;
        createUserRequest.status = UserStatusView.valueOf(controllerRequest.status.name());
        return Response.bean(userWebService.create(createUserRequest));
    }

    // use user_id to reset password
    public Response resetPassword(Request request) {
        Long id = Long.valueOf(request.pathParam("id"));
        RestUserPasswordAJAXRequest resetPasswordRequest = request.bean(RestUserPasswordAJAXRequest.class);
        BOUpdateUserPasswordRequest updateRequest = new BOUpdateUserPasswordRequest();
        updateRequest.password = resetPasswordRequest.password;
        userWebService.updatePassword(id, updateRequest);
        return Response.text("SUCCESS");
    }

    // use user id to change user status
    public Response updateStatus(Request request) {
        Long id = Long.valueOf(request.pathParam("id"));
        ActivateUserAJAXRequest activateRequest = request.bean(ActivateUserAJAXRequest.class);
        BOUpdateUserStatusRequest updateRequest = new BOUpdateUserStatusRequest();
        updateRequest.status = UserStatusView.valueOf(activateRequest.status.name());
        userWebService.updateStatus(id, updateRequest);
        return Response.text("SUCCESS");
    }

    public Response search(Request request) {
        SearchUserAJAXRequest controllerRequest = request.bean(SearchUserAJAXRequest.class);
        BOSearchUserRequest searchRequest = new BOSearchUserRequest();
        searchRequest.skip = controllerRequest.skip;
        searchRequest.limit = controllerRequest.limit;
        searchRequest.name = controllerRequest.name;
        searchRequest.email = controllerRequest.email;
        searchRequest.status = controllerRequest.status == null ? null : UserStatusView.valueOf(controllerRequest.status.name());
        BOSearchUserResponse response = service.search(searchRequest);
        return Response.bean(response);
    }
}
