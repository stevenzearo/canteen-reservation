package app.restaurant.domain;

import core.framework.mongo.MongoEnumValue;

/**
 * @author steve
 */
public enum RestaurantStatus {
    @MongoEnumValue(value = "OPEN")
    OPEN,
    @MongoEnumValue(value = "CLOSE")
    CLOSE
}
