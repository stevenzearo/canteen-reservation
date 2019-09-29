package app.canteen.web.ajax.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class SearchMealAJAXRequest {
    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 10;

    @Property(name = "name")
    public String name;

    @Property(name = "price_start")
    public Double priceStart;

    @Property(name = "price_end")
    public Double priceEnd;
}
