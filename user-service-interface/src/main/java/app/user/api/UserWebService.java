package app.user.api;

import app.user.api.user.CreateUserRequest;
import app.user.api.user.LoginRequest;
import app.user.api.user.LoginResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserView;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author steve
 */
public interface UserWebService {
    @POST
    @Path("/user")
    @ResponseStatus(HTTPStatus.CREATED)
    UserView create(CreateUserRequest request);

    @PUT
    @Path("/user")
    SearchUserResponse searchByPage(SearchUserRequest request);

    @POST
    @Path("/user/login")
    LoginResponse login(LoginRequest request);

    @POST
    @Path("/user/:id")
    void update(@PathParam("id") Long id, UpdateUserRequest request);
}
