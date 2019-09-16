package app.restaurant.domain;

import core.framework.mongo.MongoEnumValue;

/**
 * @author steve
 */
public enum MealStatus {
    @MongoEnumValue("VALID")
    VALID,
    @MongoEnumValue("INVALID")
    INVALID
}
