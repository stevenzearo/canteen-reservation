package app.canteen.web.controller;

import app.user.api.BOAdminWebService;
import app.user.api.admin.AdminLoginRequest;
import app.user.api.admin.AdminLoginResponse;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
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
        AdminLoginResponse response;
        if (!isLogin(request)) {
            String name = paramMap.get("name");
            String password = paramMap.get("password");
            AdminLoginRequest adminLoginRequest = new AdminLoginRequest();
            adminLoginRequest.name = name;
            adminLoginRequest.password = password;
            response = service.login(adminLoginRequest);
            request.session().set("admin_id", response.id.toString());
            ActionLogContext.put("adminName", adminLoginRequest.name);
        } else {
            // todo
            throw new ConflictException(Strings.format("admin has already login, name = {}", paramMap.get("name")));
        }
        return Response.bean(response); // should return a page, return a bean for test.
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
