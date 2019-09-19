package app.canteen.web.controller;

import app.user.api.BOAdminWebService;
import app.user.api.admin.AdminLoginRequest;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Map;

/**
 * @author steve
 */
public class AdminBOController {
    @Inject
    BOAdminWebService service;

    public Response login(Request request) {
        Map<String, String> paramMap = request.formParams();
        String name = paramMap.get("name");
        String password = paramMap.get("password");
        AdminLoginRequest adminLoginRequest = new AdminLoginRequest();
        adminLoginRequest.name = name;
        adminLoginRequest.password = password;
        ActionLogContext.put("adminName", adminLoginRequest.name);
        return Response.bean(service.login(adminLoginRequest));
    }
}
