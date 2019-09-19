package app.user.api;

import app.user.api.user.CreateUserRequest;
import app.user.api.user.CreateUserResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
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
public interface BOUserWebService {
    @POST
    @Path("/user/bo")
    @ResponseStatus(HTTPStatus.CREATED)
    CreateUserResponse create(CreateUserRequest request);

    @GET
    @Path("/user/bo/:id")
    UserView get(@PathParam("id") Long id);

    @PUT
    @Path("/user/bo")
    SearchUserResponse search(SearchUserRequest request);

    @PUT
    @Path("/user/bo/:id")
    void update(@PathParam("id") Long id, UpdateUserRequest request);
}
