package app.user.service;

import app.user.api.user.BOCreateUserRequest;
import app.user.api.user.BOCreateUserResponse;
import app.user.api.user.BOGetUserResponse;
import app.user.api.user.BOSearchUserRequest;
import app.user.api.user.BOSearchUserResponse;
import app.user.api.user.UpdateUserPasswordRequest;
import app.user.api.user.UpdateUserStatusRequest;
import app.user.api.user.UserStatusView;
import app.user.domain.User;
import app.user.domain.UserStatus;
import core.framework.crypto.Hash;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.ConflictException;
import core.framework.web.exception.NotFoundException;

import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

/**
 * @author steve
 */
public class BOUserService {
    @Inject
    Repository<User> repository;

    public BOCreateUserResponse create(BOCreateUserRequest request) {
        User user = new User();
        user.email = request.email;
        List<User> userList = repository.select("email = ?", user.email);
        BOCreateUserResponse response = new BOCreateUserResponse();
        if (userList.isEmpty()) {
            user.password = Hash.sha256Hex(request.password);
            user.name = request.name;
            user.status = UserStatus.valueOf(request.status.name());
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

    public void updatePassword(Long id, UpdateUserPasswordRequest request) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));
        user.password = Hash.sha256Hex(request.password);
        repository.partialUpdate(user);
    }

    public void updateStatus(Long id, UpdateUserStatusRequest request) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));
        user.status = UserStatus.valueOf(request.status.name());
        repository.partialUpdate(user);
    }

    public BOSearchUserResponse search(BOSearchUserRequest request) {
        Query<User> query = repository.select();
        query.skip(request.skip);
        query.limit(request.limit);
        if (!Strings.isBlank(request.email))
            query.where("email like ?", request.email);
        if (!Strings.isBlank(request.name))
            query.where("name like ?", request.name);
        if (request.status != null)
            query.where("status = ?", UserStatus.valueOf(request.status.name()));
        List<BOSearchUserResponse.User> users = query.fetch().stream().map(this::viewSearch).collect(Collectors.toList());
        BOSearchUserResponse response = new BOSearchUserResponse();
        response.total = (long) query.count();
        response.users = users;
        return response;
    }

    public BOGetUserResponse get(Long id) {
        User user = repository.get(id).orElseThrow(() -> new NotFoundException(Strings.format("user not found, id = {}", id)));
        BOGetUserResponse response = new BOGetUserResponse();
        response.id = user.id;
        response.name = user.name;
        response.email = user.email;
        response.status = UserStatusView.valueOf(user.status.name());
        return response;
    }

    private BOSearchUserResponse.User viewSearch(User user) {
        BOSearchUserResponse.User userView = new BOSearchUserResponse.User();
        userView.id = user.id;
        userView.name = user.name;
        userView.email = user.email;
        userView.status = user.status == null ? null : UserStatusView.valueOf(user.status.name());
        return userView;
    }
}
