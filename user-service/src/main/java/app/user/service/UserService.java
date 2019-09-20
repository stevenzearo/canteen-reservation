package app.user.service;

import app.user.api.user.CreateUserRequest;
import app.user.api.user.CreateUserResponse;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserStatusView;
import app.user.api.user.UserView;
import app.user.domain.User;
import app.user.domain.UserStatus;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;
import core.framework.web.exception.UnauthorizedException;

import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class UserService {
    @Inject
    Repository<User> repository;

    public CreateUserResponse create(CreateUserRequest request) {
        User user = new User();
        user.email = request.email;
        user.password = Hash.sha256Hex(request.password);
        user.name = request.name;
        user.status = UserStatus.VALID;
        OptionalLong insert = repository.insert(user);
        if (insert.isPresent())
            user.id = insert.getAsLong();
        CreateUserResponse response = new CreateUserResponse();
        response.id = user.id;
        response.name = user.name;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());
        return response;
    }

    public SearchUserResponse searchListByConditions(SearchUserRequest request) {
        Query<User> query = repository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        if (!Strings.isBlank(request.email))
            query.where("email like ?", request.email);
        if (!Strings.isBlank(request.name))
            query.where("name like ?", request.name);
        if (request.status != null)
            query.where("status = ?", UserStatus.valueOf(request.status.name()));
        List<UserView> userViewList = query.fetch().stream().map(this::view).collect(Collectors.toList());
        SearchUserResponse response = new SearchUserResponse();
        response.total = (long) query.count();
        response.userList = userViewList;
        return response;
    }

    public UserView login(UserLoginRequest request) {
        String sha256HexPassword = Hash.sha256Hex(request.password);
        List<User> userList = repository.select("email = ?", request.email);
        UserView userView = null;
        if (userList.size() == 1 && userList.get(0).password.equals(sha256HexPassword)) {
            userView = view(userList.get(0));
        } else {
            throw new UnauthorizedException("user email or password incorrect");
        }
        return userView;
    }

    public void update(Long id, UpdateUserRequest request) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("User not found, id = {}", id)));
        user.name = request.name;
        user.password = Hash.sha256Hex(request.password);
        user.status = request.status == null ? null : UserStatus.valueOf(request.status.name());
        user.email = request.email;
        repository.partialUpdate(user);
    }

    public UserView get(Long id) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));
        UserView userView = new UserView();
        userView.id = user.id;
        userView.name = user.name;
        userView.email = user.email;
        userView.status = UserStatusView.valueOf(user.status.name());
        return userView;
    }

    private UserView view(User user) {
        UserView userView = new UserView();
        userView.id = user.id;
        userView.name = user.name;
        userView.email = user.email;
        userView.status = user.status == null ? null : UserStatusView.valueOf(user.status.name());
        return userView;
    }
}
