package app.user.service;

import app.user.UserIntegrationExtension;
import app.user.api.user.CreateUserResponse;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserStatusView;
import app.user.api.user.UserView;
import app.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author steve
 */
class UserServiceTest extends UserIntegrationExtension {
    @Inject
    Repository<User> repository;

    @Inject
    UserService userService;

    Long userId;

    @BeforeEach
    void create() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = "1234@qq.com";
        createUserRequest.password = "qwer";
        CreateUserResponse createUserResponse = userService.create(createUserRequest);
        userId = createUserResponse.id;
        assertThat(createUserResponse.email).isEqualTo("1234@qq.com");
    }

    @Test
    void searchListByConditions() {
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.status = UserStatusView.VALID;
        assertThat(userService.searchListByConditions(searchUserRequest).userList).size().isGreaterThan(0);
        assertThat(userService.searchListByConditions(searchUserRequest).total).isGreaterThan(0);
    }

    @Test
    void login() {
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.email = "1234@qq.com";
        loginRequest.password = "qwer";
        UserView userView = userService.login(loginRequest);
        assertThat(userView.id).isEqualTo(userId);
        assertThat(userView.email).isEqualTo("1234@qq.com");
        assertThat(userView.name).isNull();
        assertThat(userView.id).isEqualTo(userId);
    }

    @Test
    void update() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.status = UserStatusView.INVALID;
        updateUserRequest.name = "steve";
        userService.update(userId, updateUserRequest);
        UserView userView = userService.get(userId);
        assertThat(userView.status).isEqualByComparingTo(UserStatusView.INVALID);
        assertThat(userView.name).isEqualTo("steve");
        assertThat(userView.id).isEqualTo(userId);
        assertThat(userView.email).isEqualTo("1234@qq.com");
    }

    @Test
    void get() {
        UserView userView = userService.get(userId);
        assertThat(userView.id).isEqualTo(userId);
        assertThat(userView.name).isNull();
        assertThat(userView.email).isEqualTo("1234@qq.com");
        assertThat(userView.status).isEqualByComparingTo(UserStatusView.VALID);
    }

    @AfterEach
    void delete() {
        repository.delete(userId);
    }
}