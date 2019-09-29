package app.canteen.web.ajax.meal;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author steve
 */
public class CreateMealAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "price")
    public Double price;
}
