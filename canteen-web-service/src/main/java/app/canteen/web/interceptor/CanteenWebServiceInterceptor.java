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
public class CanteenWebServiceInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(CanteenWebServiceInterceptor.class);

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        Response response = invocation.proceed();
        String path = invocation.context().request().path();
        logger.warn(Strings.format("request path = {}", path));
        if (!path.startsWith("/canteen/user")) {
            Optional<String> userId = invocation.context().request().session().get("user_id");
            if (userId.isEmpty()) {
                response = Response.redirect("/canteen/login", HTTPStatus.UNAUTHORIZED);
            }
        }
        return response;
    }
}
