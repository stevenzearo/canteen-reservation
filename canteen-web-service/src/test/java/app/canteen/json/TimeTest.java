package app.canteen.json;

import core.framework.json.JSON;

import java.time.ZonedDateTime;

/**
 * @author steve
 */
public class TimeTest {
    public static void main(String[] args) {
        System.out.println(JSON.toJSON(ZonedDateTime.now()));
    }
}
