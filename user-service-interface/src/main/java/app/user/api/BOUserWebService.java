package app.user.api;

import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.BOUpdateUserRequest;
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
    @Path("/bo/user")
    @ResponseStatus(HTTPStatus.CREATED)
    BOCreateUserResponse create(BOCreateUserRequest request);

    @GET
    @Path("/user/:id")
    BOGetUserResponse get(@PathParam("id") Long id); // for admin reset user's password

    @PUT
    @Path("/bo/user")
    BOSearchUserResponse search(BOSearchUserRequest request); // admin search user via name/email/status

    @PUT
    @Path("/bo/user/:id")
    void update(@PathParam("id") Long id, BOUpdateUserRequest request); // admin use user id update user
}
