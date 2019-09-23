package app.canteen.web.controller;

import app.user.api.BOUserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.GetUserResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserStatusView;
import app.user.api.user.UserView;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;

import java.util.Map;

/**
 * @author steve
 */
public class UserBOController {
    @Inject
    BOUserWebService userWebService;

    public Response createUser(Request request) {
        Map<String, String> paramMap = request.formParams();
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.name = paramMap.get("name");
        createUserRequest.password = paramMap.get("password");
        createUserRequest.email = paramMap.get("email");
        return Response.bean(userWebService.create(createUserRequest));
    }

    // use user_id to reset password
    public Response resetPassword(Request request) {
        Map<String, String> paramMap = request.formParams();
        Long userId;
        try {
            userId = Long.valueOf(paramMap.get("user_id"));
        } catch (NumberFormatException e) {
            throw new BadRequestException(Strings.format("invalid user id, user id must be Long, user_id = {}", paramMap.get("user_id")));
        }
        GetUserResponse response = userWebService.get(userId);
        if (response != null) {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.password = paramMap.get("new_password");
            userWebService.update(response.id, updateUserRequest);
        } else {
            throw new NotFoundException(Strings.format("user not found, id = {}", paramMap.get("user_id")));
        }
        return Response.bean(response);
    }

    // use user id to change user status
    public Response changeUserStatus(Request request) {
        Map<String, String> paramMap = request.queryParams();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        Long id = Long.valueOf(paramMap.get("id"));
        userWebService.get(id);
        updateUserRequest.status = JSON.fromJSON(UserStatusView.class, paramMap.get("status"));
        userWebService.update(JSON.fromJSON(Long.class, paramMap.get("id")), updateUserRequest);
        return Response.text("SUCCESS"); // should return a module, return text for test.
    }
}
