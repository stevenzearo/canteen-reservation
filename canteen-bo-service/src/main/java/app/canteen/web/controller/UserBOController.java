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
        BOCreateUserRequest createUserRequest = new BOCreateUserRequest();
        createUserRequest.name = paramMap.get("name");
        createUserRequest.password = paramMap.get("password");
        createUserRequest.email = paramMap.get("email");
        return Response.bean(userWebService.create(createUserRequest)); // should return a page, return text for test.
    }

    // use user_id to reset password
    public Response resetPassword(Request request) {
        Map<String, String> paramMap = request.formParams();
        try {
            Long userId = Long.valueOf(paramMap.get("user_id"));
            String password = paramMap.get("password");
            if (!Strings.isBlank(password)) {
                userWebService.updatePassword(userId, password);
            } else {
                throw new BadRequestException("password can not be blank");
            }
        } catch (NumberFormatException e) {
            throw new BadRequestException("user id should be Long");
        }
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }

    // use user id to change user status
    public Response activate(Request request) {
        Map<String, String> paramMap = request.queryParams();
        BOUpdateUserRequest updateUserRequest = new BOUpdateUserRequest();
        try {
            Long id = Long.valueOf(paramMap.get("id"));
            UserStatusView status = UserStatusView.valueOf(paramMap.get("status"));
            userWebService.updateStatus(id, status);
        } catch (NumberFormatException e) {
            throw new BadRequestException("user id should be Long");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("status incorrect");
        }
        return Response.text("SUCCESS"); // should return a page, return text for test.
    }
}
