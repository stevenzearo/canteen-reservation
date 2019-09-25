package app.restaurant.api.restaurant;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
// todo
public class SearchResponse {
    @Property(name = "total")
    public Long total;

    // todo inner class
    @Property(name = "restaurant_list")
    public List<RestaurantView> restaurantList;
}