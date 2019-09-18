package app.user.service;

import app.user.UserIntegrationExtension;
import app.user.api.user.LoginStatus;
import app.user.api.user.CreateUserRequest;
import app.user.api.user.SearchUserRequest;
import app.user.api.user.UpdateUserRequest;
import app.user.api.user.UserLoginRequest;
import app.user.api.user.UserLoginResponse;
import app.user.api.user.UserStatus;
import app.user.api.user.UserView;
import app.user.domain.User;
import core.framework.crypto.Hash;
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
        UserView userView = userService.create(createUserRequest);
        userId = userView.id;
        assertThat(userView.password).isEqualTo(Hash.sha256Hex("qwer"));
        assertThat(userView.email).isEqualTo("1234@qq.com");
    }

    @Test
    void searchListByConditions() {
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.status = UserStatus.VALID;
        assertThat(userService.searchListByConditions(searchUserRequest).userViewList).size().isGreaterThan(0);
        assertThat(userService.searchListByConditions(searchUserRequest).total).isGreaterThan(0);
    }

    @Test
    void login() {
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.email = "1234@qq.com";
        loginRequest.password = "qwer";
        UserLoginResponse login = userService.login(loginRequest);
        assertThat(login.status).isEqualTo(LoginStatus.SUCCESS);
        assertThat(login.userView.email).isEqualTo("1234@qq.com");
        assertThat(login.userView.name).isNull();
        assertThat(login.userView.password).isEqualTo(Hash.sha256Hex("qwer"));
        assertThat(login.userView.id).isEqualTo(userId);
    }

    @Test
    void update() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.status = UserStatus.INVALID;
        updateUserRequest.name = "steve";
        userService.update(userId, updateUserRequest);
        UserView userView = userService.get(userId);
        assertThat(userView.status).isEqualByComparingTo(UserStatus.INVALID);
        assertThat(userView.name).isEqualTo("steve");
        assertThat(userView.id).isEqualTo(userId);
        assertThat(userView.email).isEqualTo("1234@qq.com");
        assertThat(userView.password).isEqualTo(Hash.sha256Hex("qwer"));
    }

    @Test
    void get() {
        UserView userView = userService.get(userId);
        assertThat(userView.id).isEqualTo(userId);
        assertThat(userView.name).isNull();
        assertThat(userView.email).isEqualTo("1234@qq.com");
        assertThat(userView.status).isEqualByComparingTo(UserStatus.VALID);
        assertThat(userView.password).isEqualTo(Hash.sha256Hex("qwer"));
    }

    @AfterEach
    void delete() {
        repository.delete(userId);
    }
}