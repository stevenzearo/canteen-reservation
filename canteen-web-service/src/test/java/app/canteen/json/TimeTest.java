package app.canteen.json;

import core.framework.json.JSON;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class TimeTest {
    private final Logger logger = LoggerFactory.getLogger(TimeTest.class);

    @Test
    void test() {
        logger.info(JSON.toJSON(ZonedDateTime.now()));
    }
}
