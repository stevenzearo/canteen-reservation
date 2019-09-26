package app.user.service;

import app.user.api.user.CreateUserRequest;
import app.user.api.user.CreateUserResponse;
import app.user.api.user.GetUserResponse;
import app.user.api.user.SearchUserResponse;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserStatusView;
import app.user.api.user.UserLoginResponse;
import app.user.domain.User;
import app.user.domain.UserStatus;
import core.framework.crypto.Hash;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;
import core.framework.web.exception.UnauthorizedException;

import java.util.List;
import java.util.OptionalLong;

/**
 * @author steve
 */
public class UserService {
    @Inject
    Repository<User> repository;

    public CreateUserResponse create(CreateUserRequest request) {
        User user = new User();
        user.email = request.email;
        List<User> userList = repository.select("email = ?", user.email);
        CreateUserResponse response = new CreateUserResponse();
        if (!userList.isEmpty()) {
            user.password = Hash.sha256Hex(request.password);
            user.name = request.name;
            user.status = UserStatus.INVALID;
            OptionalLong userId = repository.insert(user);
            if (userId.isPresent()) user.id = userId.getAsLong();
            response.id = user.id;
            response.name = user.name;
            response.email = user.email;
            response.status = UserStatusView.valueOf(user.status.name());
        } else {
            throw new ConflictException("email has been registered");
        }
        return response;
    }

    public UserLoginResponse login(UserLoginRequest request) {
        String sha256HexPassword = Hash.sha256Hex(request.password);
        List<User> userList = repository.select("email = ?", request.email);
        UserLoginResponse userView;
        if (userList.size() == 1 && userList.get(0).password.equals(sha256HexPassword)) {
            if (userList.get(0).status == UserStatus.VALID) {
                userView = viewLogin(userList.get(0));
            } else {
                throw new UnauthorizedException("user has not be activated yet");
            }
        } else {
            throw new UnauthorizedException("user email or password incorrect");
        }
        return userView;
    }

    public GetUserResponse get(Long id) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));
        GetUserResponse response = new GetUserResponse();
        response.id = user.id;
        response.name = user.name;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());
        return response;
    }

    private UserLoginResponse viewLogin(User user) {
        UserLoginResponse userView = new UserLoginResponse();
        userView.id = user.id;
        userView.name = user.name;
        userView.email = user.email;
        userView.status = UserStatusView.valueOf(user.status.name());
        return userView;
    }
}
