package app.canteen.web.ajax;

import app.user.api.BOUserWebService;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author steve
 */
public class UserBOAJAXController {
    @Inject
    BOUserWebService service;

    public Response searchByPage(Request request) {
        BOSearchUserRequest searchRequest = request.bean(BOSearchUserRequest.class);
        BOSearchUserResponse response = service.search(searchRequest);
        return Response.bean(response);
    }
}
