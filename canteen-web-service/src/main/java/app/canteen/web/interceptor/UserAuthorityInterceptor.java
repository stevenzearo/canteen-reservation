package app.canteen.web.interceptor;

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
public class UserAuthorityInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(UserAuthorityInterceptor.class);

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        Response response = invocation.proceed();
        String path = invocation.context().request().path();
        logger.warn(Strings.format("request path = {}", path));
        if (!path.startsWith("/canteen/user")) {
            Optional<String> userId = invocation.context().request().session().get("user_id");
            if (userId.isEmpty()) {
                response = Response.text("UNAUTHORIZED"); // should redirect to login page return text for test
            }
        }
        return response;
    }
}
