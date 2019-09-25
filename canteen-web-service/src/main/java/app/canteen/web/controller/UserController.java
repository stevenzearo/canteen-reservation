package app.canteen.web.controller;

import app.canteen.web.page.IndexPage;
import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserView;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.ConflictException;

import java.util.Map;

/**
 * @author steve
 */
public class UserController {
    @Inject
    UserWebService service;

    public Response register(Request request) {
        Response response;
        Map<String, String> paramMap = request.formParams();
        if (!isRegistered(paramMap)) {
            CreateUserRequest createRequest = new CreateUserRequest();
            createRequest.name = paramMap.get("name");
            createRequest.email = paramMap.get("email");
            createRequest.password = paramMap.get("password");
            response = Response.bean(service.create(createRequest));
        } else {
            throw new ConflictException(Strings.format("email has already registered, email = {}", paramMap.get("email")));
        }
        return response;
    }

    private boolean isRegistered(Map<String, String> paramMap) {
        SearchUserRequest searchRequest = new SearchUserRequest();
        searchRequest.email = paramMap.get("email");
        SearchUserResponse searchUserResponse = service.search(searchRequest);
        return searchUserResponse.total > 0;
    }

    public Response login(Request request) {
        Response response;
        if (!isLogin(request)) {
            Map<String, String> paramMap = request.formParams();
            UserLoginRequest loginRequest = new UserLoginRequest();
            loginRequest.email = paramMap.get("email");
            loginRequest.password = paramMap.get("password");
            UserView userView = service.login(loginRequest);
            response = Response.bean(userView);
            request.session().set("user_id", String.valueOf(userView.id));
        } else {
            response = Response.text("ALREADY LOGIN"); // should return a module, return text for test.
        }
        return response;
    }

    public Response logout(Request request) {
        if (isLogin(request)) {
            request.session().set("user_id", null);
        }
        return Response.text("LOGOUT SUCCESSFULLY"); // should return a module, return text for test.
    }

    private boolean isLogin(Request request) {
        return request.session().get("user_id").isPresent();
    }

}
