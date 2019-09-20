import app.restaurant.api.restaurant.RestaurantStatusView;
import core.framework.json.JSON;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class Test {
    @org.junit.jupiter.api.Test
    void test() {
        System.out.println(JSON.toJSON(ZonedDateTime.now()));
        System.out.println(JSON.toEnumValue(RestaurantStatusView.OPEN));
    }
}
