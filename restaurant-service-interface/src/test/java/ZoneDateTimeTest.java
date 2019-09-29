import core.framework.json.JSON;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class ZoneDateTimeTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZoneDateTimeTest.class);

    @Test
    void test() {
        LOGGER.info(JSON.toJSON(ZonedDateTime.now()));
        LOGGER.info(ZonedDateTime.now().toString());
    }
}
