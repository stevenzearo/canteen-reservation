package app.canteen.web.controller;

import app.canteen.web.controller.admin.AdminLoginRequest;
import app.user.api.BOAdminWebService;
import app.user.api.admin.BOAdminLoginRequest;
import app.user.api.admin.BOAdminLoginResponse;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;
import core.framework.web.Request;
import core.framework.web.Response;

/**
 * @author steve
 */
public class AdminController {
    @Inject
    BOAdminWebService service;

    public Response login(Request request) {
        Response response;
        if (!isLogin(request)) {
            AdminLoginRequest controllerRequest = request.bean(AdminLoginRequest.class);
            BOAdminLoginRequest loginRequest = new BOAdminLoginRequest();
            loginRequest.name = controllerRequest.name;
            loginRequest.password = controllerRequest.password;
            BOAdminLoginResponse loginResponse = service.login(loginRequest);
            request.session().set("admin_id", loginResponse.id.toString());
            ActionLogContext.put("adminName", loginResponse.name);
            response = Response.bean(loginResponse); // should return a page, return a bean for test.
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
