package app.reservation;

import app.reservation.api.kafka.CancellingReservationMessage;
import app.reservation.api.kafka.SendingEmailReservationMessage;
import core.framework.module.App;
import core.framework.module.KafkaConfig;
import core.framework.module.SystemModule;

/**
 * @author steve
 */
public class ReservationApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        KafkaConfig kafkaConfig = kafka();
//        kafkaConfig.uri(requiredProperty("sys.kafka.URI"));
        kafkaConfig.publish("sending-email-reservation", SendingEmailReservationMessage.class);
        kafkaConfig.publish("cancelling-reservation", CancellingReservationMessage.class);
        load(new ReservationModule());
    }
}
