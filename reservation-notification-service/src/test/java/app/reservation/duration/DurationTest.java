package app.reservation.duration;

import core.framework.json.JSON;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class DurationTest {
    private final Logger logger = LoggerFactory.getLogger(DurationTest.class);

    @Test
    void test() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime time = now.plusDays(2);
        Duration between = Duration.between(now.plusMinutes(10), time);
        logger.info(JSON.toJSON(between));
    }
}
