package app.reservation;

import app.reservation.api.message.ReservationMessage;
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
        kafkaConfig.publish("reservation", ReservationMessage.class);
        load(new ReservationModule());
    }
}
