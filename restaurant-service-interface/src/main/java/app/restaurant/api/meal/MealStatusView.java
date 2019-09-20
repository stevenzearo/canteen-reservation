package app.restaurant.api.meal;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public enum MealStatusView {
    @Property(name = "VALID")
    VALID,
    @Property(name = "INVALID")
    INVALID
}