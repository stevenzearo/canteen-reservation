package app.user.service;

import app.user.UserIntegrationExtension;
import app.user.api.admin.BOAdminLoginRequest;
import app.user.domain.Admin;
import core.framework.crypto.Hash;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author steve
 */
class BOAdminServiceTest extends UserIntegrationExtension {
    @Inject
    Repository<Admin> repository;

    @Inject
    BOAdminService BOAdminService;

    Admin admin;

    @BeforeEach
    void create() {
        admin = new Admin();
        admin.name = "steve";
        admin.password = Hash.sha256Hex("1234");
        OptionalLong insert = repository.insert(admin);
        if (insert.isPresent()) admin.id = insert.getAsLong();
    }

    @Test
    void login() {
        BOAdminLoginRequest BOAdminLoginRequest = new BOAdminLoginRequest();
        BOAdminLoginRequest.name = "steve";
        BOAdminLoginRequest.password = "1234";
        assertThat(BOAdminService.login(BOAdminLoginRequest)).isNotNull();
    }

    @AfterEach
    void delete() {
        repository.delete(admin.id);
    }
}