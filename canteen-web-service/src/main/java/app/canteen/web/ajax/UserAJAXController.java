package app.canteen.web.ajax;

import app.canteen.web.ajax.user.UserRegistryAJAXRequest;
import app.canteen.web.ajax.user.UserLoginAJAXRequest;
import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.UserLoginResponse;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class UserAJAXController {
    @Inject
    UserWebService service;

    public Response register(Request request) {
        UserRegistryAJAXRequest controllerRequest = request.bean(UserRegistryAJAXRequest.class);
        CreateUserRequest createRequest = new CreateUserRequest();
        createRequest.name = controllerRequest.name;
        createRequest.email = controllerRequest.email;
        createRequest.password = controllerRequest.password;
        return Response.bean(service.create(createRequest));
    }

    public Response login(Request request) {
        Response response;
        if (!isLogin(request)) {
            UserLoginAJAXRequest controllerRequest = request.bean(UserLoginAJAXRequest.class);
            app.user.api.user.UserLoginRequest loginRequest = new app.user.api.user.UserLoginRequest();
            loginRequest.email = controllerRequest.email;
            loginRequest.password = controllerRequest.password;
            UserLoginResponse loginResponse = service.login(loginRequest);
            response = Response.bean(loginResponse);
            request.session().set("user_id", loginResponse.id.toString());
        } else {
            response = Response.text("ALREADY LOGIN");
        }
        return response;
    }

    public Response logout(Request request) {
        if (isLogin(request)) {
            request.session().set("user_id", null);
        }
        return Response.text("LOGOUT SUCCESSFULLY");
    }

    private boolean isLogin(Request request) {
        return request.session().get("user_id").isPresent();
    }
}
