package app.restaurant.domain;

import core.framework.mongo.MongoEnumValue;

/**
 * @author steve
 */
public enum RestaurantStatus {
    @MongoEnumValue("OPEN")
    OPEN,
    @MongoEnumValue("CLOSE")
    CLOSE
}