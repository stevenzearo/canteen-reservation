package app.canteen.web.controller;

import app.canteen.web.controller.user.UserRegistryRequest;
import app.canteen.web.controller.user.UserLoginRequest;
import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.UserLoginResponse;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class UserController {
    @Inject
    UserWebService service;

    public Response register(Request request) {
        UserRegistryRequest controllerRequest = request.bean(UserRegistryRequest.class);
        CreateUserRequest createRequest = new CreateUserRequest();
        createRequest.name = controllerRequest.name;
        createRequest.email = controllerRequest.email;
        createRequest.password = controllerRequest.password;
        return Response.bean(service.create(createRequest)); // should return a page, return a bean for test.
    }

    public Response login(Request request) {
        Response response;
        if (!isLogin(request)) {
            UserLoginRequest controllerRequest = request.bean(UserLoginRequest.class);
            app.user.api.user.UserLoginRequest loginRequest = new app.user.api.user.UserLoginRequest();
            loginRequest.email = controllerRequest.email;
            loginRequest.password = controllerRequest.password;
            UserLoginResponse loginResponse = service.login(loginRequest);
            response = Response.bean(loginResponse); // should return a page, return a bean for test.
            request.session().set("user_id", loginResponse.id.toString());
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
