package app.canteen.web.ajax;

import app.user.api.UserWebService;
import app.user.api.user.SearchResponse;
import app.user.api.user.SearchUserRequest;
import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Map;

/**
 * @author steve
 */
public class UserBOAJAXController {
    @Inject
    UserWebService service;

    public Response searchListByPage(Request request) {
        Map<String, String> paramMap = request.queryParams();
        Integer pageNumber = Integer.valueOf(paramMap.get("page_number"));
        Integer pageSize = Integer.valueOf(paramMap.get("page_size"));
        Integer skip = 0;
        Integer limit = 10;
        if (pageNumber > 0 && pageSize > 0) {
            limit = pageSize;
            skip = (pageNumber - 1) * pageSize;
        }
        SearchUserRequest userRequest = new SearchUserRequest();
        userRequest.skip = skip;
        userRequest.limit = limit;
        SearchResponse response = service.searchListByConditions(userRequest);
        return Response.bean(response);
    }
}
