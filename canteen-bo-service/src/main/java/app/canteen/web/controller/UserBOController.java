package app.canteen.web.controller;

import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.SearchResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserStatus;
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
    UserWebService userWebService;

    public Response createUser(Request request) {
        Map<String, String> paramMap = request.queryParams();
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
        SearchResponse searchResponse = userWebService.searchListByConditions(searchUserRequest);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        String status = "failed";
        if (searchResponse.userViewList.size() == 1
            && searchResponse.userViewList.get(0).email.equals(searchUserRequest.email)
            && searchResponse.userViewList.get(0).password.equals(paramMap.get("pre_password"))
        ) {
            updateUserRequest.password = paramMap.get("new_password");
            userWebService.update(searchResponse.userViewList.get(0).id, updateUserRequest);
            status = "success";
        }
        return Response.text(status);
    }

    // use user id to change user status
    public void changeUserStatus(Request request) {
        Map<String, String> paramMap = request.queryParams();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        Long id = Long.valueOf(paramMap.get("id"));
        userWebService.get(id);
        updateUserRequest.status = (JSON.fromJSON(UserStatus.class, paramMap.get("status")));
        userWebService.update(JSON.fromJSON(Long.class, paramMap.get("id")), updateUserRequest);
    }
}
