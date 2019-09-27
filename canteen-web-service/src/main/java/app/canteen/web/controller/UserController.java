package app.canteen.web.controller;

import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

import java.util.Map;

/**
 * @author steve
 */
public class UserController {
    @Inject
    UserWebService service;

    public Response register(Request request) {
        Map<String, String> paramMap = request.formParams();
        CreateUserRequest createRequest = new CreateUserRequest();
        String name = paramMap.get("name");
        String email = paramMap.get("email");
        String password = paramMap.get("password");
        Response response;
        if (!Strings.isBlank(email) && !Strings.isBlank(password)) {
            createRequest.name = name;
            createRequest.email = email;
            createRequest.password = password;
            response = Response.bean(service.create(createRequest));
        } else {
            throw new BadRequestException("email and password can not be blank or null");
        }
        return response; // should return a page, return a bean for test.
    }

    public Response login(Request request) {
        Response response;
        if (!isLogin(request)) {
            Map<String, String> paramMap = request.formParams();
            UserLoginRequest loginRequest = new UserLoginRequest();
            String email = paramMap.get("email");
            String password = paramMap.get("password");
            if (!Strings.isBlank(email) && !Strings.isBlank(password)) {
                loginRequest.email = paramMap.get("email");
                loginRequest.password = paramMap.get("password");
                UserLoginResponse userView = service.login(loginRequest);
                response = Response.bean(userView); // should return a page, return a bean for test.
            } else {
                throw new BadRequestException("email or password incorrect");
            }
        } else {
            response = Response.text("ALREADY LOGIN"); // should return a page, return text for test.
        }
        return response;
    }

    public Response logout(Request request) {
        if (isLogin(request)) {
            request.session().set("user_id", null);
        }
        return Response.text("LOGOUT SUCCESSFULLY"); // should return a page, return text for test.
    }

    private boolean isLogin(Request request) {
        return request.session().get("user_id").isPresent();
    }
}
