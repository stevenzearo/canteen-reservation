package app.user;

import core.framework.test.Context;
import core.framework.test.IntegrationExtension;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author steve
 */
@ExtendWith(IntegrationExtension.class)
@Context(module = TestModule.class)
public class UserIntegrationExtension {
}

