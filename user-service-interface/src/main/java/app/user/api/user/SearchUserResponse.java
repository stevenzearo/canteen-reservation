package app.user.api.user;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author steve
 */
public class SearchUserResponse {
    @Property(name = "total")
    public Long total;

    @Property(name = "user_list")
    public List<UserView> userList;
}
