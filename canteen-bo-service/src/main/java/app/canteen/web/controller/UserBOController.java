package app.canteen.web.controller;

import app.user.api.BOUserWebService;
import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.web.Request;
import core.framework.web.Response;

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

    // use email and pre_password to reset password
    public Response resetPassword(Request request) {
        Map<String, String> paramMap = request.queryParams();
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.email = paramMap.get("email");
        SearchUserResponse searchUserResponse = userWebService.search(searchUserRequest);
        String status = "FAILED";
        if (searchUserResponse.userList.size() == 1
            && searchUserResponse.userList.get(0).email.equals(searchUserRequest.email)
            && searchUserResponse.userList.get(0).password.equals(paramMap.get("pre_password"))
        ) {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            updateUserRequest.password = paramMap.get("new_password");
            userWebService.update(searchUserResponse.userList.get(0).id, updateUserRequest);
            status = "SUCCESS";
        }
        return Response.text(status);
    }

    // use user id to change user status
    public void changeUserStatus(Request request) {
        Map<String, String> paramMap = request.queryParams();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        Long id = Long.valueOf(paramMap.get("id"));
        userWebService.get(id);
        updateUserRequest.status = JSON.fromJSON(UserStatusView.class, paramMap.get("status"));
        userWebService.update(JSON.fromJSON(Long.class, paramMap.get("id")), updateUserRequest);
    }
}
