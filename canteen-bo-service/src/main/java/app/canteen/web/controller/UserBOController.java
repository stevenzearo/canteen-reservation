package app.canteen.web.controller;

import app.user.api.BOUserWebService;
import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOUpdateUserRequest;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

import java.util.Map;

/**
 * @author steve
 */
public class UserBOController {
    @Inject
    BOUserWebService userWebService;

    public Response createUser(Request request) {
        Map<String, String> paramMap = request.formParams();
        String name = paramMap.get("name");
        String password = paramMap.get("password");
        String email = paramMap.get("email");
        BOCreateUserRequest createUserRequest = new BOCreateUserRequest();
        if (!Strings.isBlank("password") && !Strings.isBlank(email)) {
            createUserRequest.name = name;
            createUserRequest.password = password;
            createUserRequest.email = email;
        } else {
            throw new BadRequestException("email and password can not be blank");
        }
        return Response.bean(userWebService.create(createUserRequest)); // should return a page, return text for test.
    }

    // use user_id to reset password
    public Response resetPassword(Request request) {
        Map<String, String> paramMap = request.formParams();
        Long userId;
        String password;
        try {
            userId = Long.valueOf(paramMap.get("user_id"));
            password = paramMap.get("password");
            if (Strings.isBlank(password)) throw new BadRequestException("password can not be blank");
        } catch (NumberFormatException e) {
            throw new BadRequestException("user id should be Long");
        }
        userWebService.updatePassword(userId, password);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }

    // use user id to change user status
    public Response activate(Request request) {
        Map<String, String> paramMap = request.queryParams();
        BOUpdateUserRequest updateUserRequest = new BOUpdateUserRequest();
        Long id;
        UserStatusView status;
        try {
            id = Long.valueOf(paramMap.get("id"));
            status = UserStatusView.valueOf(paramMap.get("status"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("user id should be Long");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("status incorrect");
        }
        userWebService.updateStatus(id, status);
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }
}
