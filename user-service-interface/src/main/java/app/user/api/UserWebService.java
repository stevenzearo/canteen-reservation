package app.user.api;

import app.user.api.user.CreateUserRequest;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserView;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
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
    SearchResponse searchListByConditions(SearchUserRequest request);

    @POST
    @Path("/user/login")
    UserLoginResponse login(UserLoginRequest request);

    @POST
    @Path("/user/:id")
    void update(@PathParam("id") Long id, UpdateUserRequest request);

    @GET
    @Path("/user/:id")
    UserView get(@PathParam("id") Long id);
}
