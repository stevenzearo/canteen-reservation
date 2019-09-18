package app.canteen.web.controller;

import app.user.api.UserWebService;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.SearchResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import com.sun.net.httpserver.HttpServer;
import core.framework.impl.web.HTTPErrorHandler;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Map;
import java.util.Optional;

/**
 * @author steve
 */
public class UserController {
    @Inject
    UserWebService service;

    public Response register(Request request) {
        Response response = null;
        if (!isRegistered(request)) {
            Map<String, String> paramMap = request.queryParams();
            CreateUserRequest createRequest = new CreateUserRequest();
            createRequest.name = paramMap.get("name");
            createRequest.email = paramMap.get("email");
            createRequest.password = paramMap.get("password");
            response = Response.bean(service.create(createRequest));
        }
        return response;
    }

    private boolean isRegistered(Request request) {
        Map<String, String> paramMap = request.queryParams();
        SearchUserRequest searchRequest = new SearchUserRequest();
        searchRequest.email = paramMap.get("email");
        SearchResponse searchResponse = service.searchListByConditions(searchRequest);
        return searchResponse.total > 0;
    }

    public Response login(Request request) {
        Response response = null;
        if (!isLogin(request)) {
            Map<String, String> paramMap = request.queryParams();
            UserLoginRequest loginRequest = new UserLoginRequest();
            loginRequest.email = paramMap.get("email");
            loginRequest.password = paramMap.get("password");
            UserLoginResponse login = service.login(loginRequest);
            response = Response.bean(login);
            request.session().set("user_id", String.valueOf(login.userView.id));
        }
        return response;
    }

    public boolean isLogin(Request request) {
        return request.session().get("user_id").isPresent();
    }

}
