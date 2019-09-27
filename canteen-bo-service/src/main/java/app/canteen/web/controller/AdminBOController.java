package app.canteen.web.controller;

import app.user.api.BOAdminWebService;
import app.user.api.admin.BOAdminLoginRequest;
import app.user.api.admin.BOAdminLoginResponse;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.ConflictException;

import java.util.Map;

/**
 * @author steve
 */

// todo
public class AdminBOController {
    @Inject
    BOAdminWebService service;

    public Response login(Request request) {
        Map<String, String> paramMap = request.formParams();
        Response response;
        if (!isLogin(request)) {
            String name = paramMap.get("name");
            String password = paramMap.get("password");
            if (!Strings.isBlank(name) && !Strings.isBlank(password)) {
                BOAdminLoginRequest BOAdminLoginRequest = new BOAdminLoginRequest();
                BOAdminLoginRequest.name = name;
                BOAdminLoginRequest.password = password;
                BOAdminLoginResponse loginResponse = service.login(BOAdminLoginRequest);
                request.session().set("admin_id", loginResponse.id.toString());
                ActionLogContext.put("adminName", BOAdminLoginRequest.name);
                response = Response.bean(loginResponse); // should return a page, return a bean for test.
            } else {
                throw new BadRequestException("name or password can not be blank");
            }
        } else {
            response = Response.text("ALREADY LOGIN"); // should return a page, return text for test.
        }
        return response;
    }

    private boolean isLogin(Request request) {
        return request.session().get("admin_id").isPresent();
    }

    public Response logout(Request request) {
        if (isLogin(request)) {
            request.session().set("admin_id", null);
        }
        return Response.text("LOGOUT SUCCESSFULLY"); // should return a page, return text for test.
    }

}
