package app.restaurant.api.restaurant;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class SearchResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "restaurantList")
    public List<RestaurantView> restaurantViewList;
}