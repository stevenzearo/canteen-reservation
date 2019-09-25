package app.canteen.web.interceptor;

import core.framework.api.http.HTTPStatus;
import core.framework.util.Strings;
import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author steve
 */
public class CanteenBOServiceInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(CanteenBOServiceInterceptor.class);

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        Response response = invocation.proceed();
        // todo
        String path = invocation.context().request().path();
        logger.warn(Strings.format("request path = {}", path));
        if (!path.startsWith("/canteen/bo/admin")) {
            Optional<String> adminId = invocation.context().request().session().get("admin_id");
            if (adminId.isEmpty()) {
                // todo
                response = Response.redirect("/canteen/bo/admin/login", HTTPStatus.UNAUTHORIZED);
            }
        }
        return response;
    }
}
