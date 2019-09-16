package app.restaurant.api.restaurant;

import core.framework.api.json.Property;

/**
 * @author steve
 */
public enum RestaurantStatus {
    @Property(name = "OPEN")
    OPEN,
    @Property(name = "CLOSE")
    CLOSE
}
