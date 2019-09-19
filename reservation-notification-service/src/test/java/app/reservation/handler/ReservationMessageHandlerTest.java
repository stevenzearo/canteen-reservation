package app.reservation.handler;

import app.reservation.api.message.ReservationMessage;
import core.framework.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author steve
 */
class ReservationMessageHandlerTest {
    @Inject
    ReservationMessageHandler handler;
    @Test
    void handle() throws Exception {
        ReservationMessage message = new ReservationMessage();
        message.userId = 1L;
        message.reservationId = UUID.randomUUID().toString();
        message.reservationDeadline = ZonedDateTime.now().plusSeconds(30);
        handler.handle(UUID.randomUUID().toString(), message);
    }
}