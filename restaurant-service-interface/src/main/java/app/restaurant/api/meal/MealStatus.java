package app.restaurant.api.meal;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public enum MealStatus {
    @Property(name = "VALID")
    VALID,
    @Property(name = "INVALID")
    INVALID
}