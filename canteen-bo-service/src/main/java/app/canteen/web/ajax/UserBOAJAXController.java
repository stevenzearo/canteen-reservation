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

    private final Logger logger = LoggerFactory.getLogger(UserBOAJAXController.class);
    public Response searchListByPage(Request request) {
        Map<String, String> paramMap = request.queryParams();
        int pageNumber = 1;
        int pageSize = 10;
        try {
            pageNumber = Integer.parseInt(paramMap.get("page_number"));
            pageSize = Integer.parseInt(paramMap.get("page_size"));
        } catch (NumberFormatException ignored) {
            logger.warn("missing page_number and page_size using default setting");
        }
        Integer skip = 0;
        Integer limit = 10;
        if (pageNumber > 0 && pageSize > 0) {
            limit = pageSize;
            skip = (pageNumber - 1) * pageSize;
        } else {
            logger.warn("invalid page_number or page_size using default setting");
        }
        SearchUserRequest userRequest = new SearchUserRequest();
        userRequest.skip = skip;
        userRequest.limit = limit;
        SearchUserResponse response = service.search(userRequest);
        return Response.bean(response);
    }
}
