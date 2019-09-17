package app.user.api.user;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class SearchResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "user_list")
    public List<UserView> userViewList;
}
