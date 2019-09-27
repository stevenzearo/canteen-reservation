package app.canteen.web.ajax;

import app.canteen.web.ajax.user.SearchUserAJAXRequest;
import app.user.api.BOUserWebService;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.UserStatusView;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class UserAJAXController {
    @Inject
    BOUserWebService service;

    public Response search(Request request) {
        SearchUserAJAXRequest controllerRequest = request.bean(SearchUserAJAXRequest.class);
        BOSearchUserRequest searchRequest = new BOSearchUserRequest();
        searchRequest.skip = controllerRequest.skip;
        searchRequest.limit = controllerRequest.limit;
        searchRequest.name = controllerRequest.name;
        searchRequest.email = controllerRequest.email;
        searchRequest.status = UserStatusView.valueOf(controllerRequest.status.name());
        BOSearchUserResponse response = service.search(searchRequest);
        return Response.bean(response);
    }
}
