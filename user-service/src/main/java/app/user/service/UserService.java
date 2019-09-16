package app.user.service;

import app.user.api.user.CreateUserRequest;
import app.user.api.user.LoginRequest;
import app.user.api.user.LoginResponse;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserView;
import app.user.domain.User;
import app.user.domain.UserStatus;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class UserService {
    @Inject
    Repository<User> repository;

    public UserView create(CreateUserRequest request) {
        User user = new User();
        user.email = request.email;
        user.password = Hash.sha256Hex(request.password);
        user.name = request.name;
        user.status = UserStatus.VALID;
        OptionalLong insert = repository.insert(user);
        if (insert.isPresent())
            user.id = insert.getAsLong();
        return view(user);
    }

    public SearchUserResponse searchByPage(SearchUserRequest request) {
        Query<User> query = repository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        List<UserView> userViewList = query.fetch().stream().map(this::view).collect(Collectors.toList());
        SearchUserResponse response = new SearchUserResponse();
        response.total = query.count();
        response.userViewList = userViewList;
        return response;
    }

    public LoginResponse login(LoginRequest request) {
        String sha256Hex = Hash.sha256Hex(request.password);
        List<User> userList = repository.select("email = ?", request.email);
        LoginResponse loginResponse = new LoginResponse();
        if (userList.size() == 1 && userList.get(0).password.equals(sha256Hex)) {
            loginResponse.status = true;
            loginResponse.userView = view(userList.get(0));
        }
        return loginResponse;
    }

    public void update(Long id, UpdateUserRequest request) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));
        user.name = request.name;
        user.password = request.password;
        user.status = request.status == null ? null : UserStatus.valueOf(request.status.name());
        user.email = request.email;
        repository.update(user);
    }

    private UserView view(User user) {
        UserView userView = new UserView();
        userView.id = user.id;
        userView.name = user.name;
        userView.password = user.password;
        userView.email = user.email;
        userView.status = user.status == null ? null : app.user.api.user.UserStatus.valueOf(user.status.name());
        return userView;
    }
}
