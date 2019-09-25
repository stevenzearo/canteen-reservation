package app.canteen.web.ajax;

import app.user.api.BOUserWebService;
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
        Map<String, String> paramMap = request.queryParams();
        SearchUserRequest userRequest = new SearchUserRequest();
        userRequest.skip = Integer.parseInt(paramMap.get("skip"));
        userRequest.limit = Integer.parseInt(paramMap.get("limit"));
        SearchUserResponse response = service.search(userRequest);
        return Response.bean(response);
    }
}
