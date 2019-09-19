package add.reservation.duration;

import app.reservation.api.reservation.ReservationStatus;
import app.reservation.api.reservation.ReservationView;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

/**
 * @author steve
 */
public class DurationTest {
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime time = now.plusDays(2);
        Duration between = Duration.between(now.plusMinutes(10), time);
        System.out.println(between);
        ReservationView view = new ReservationView();
        view.status = ReservationStatus.OK;
//        System.out.println(ReservationStatus.OK.equals(view.status));
    }
}
