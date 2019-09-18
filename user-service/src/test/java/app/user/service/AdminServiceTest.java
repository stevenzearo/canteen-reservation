package app.user.service;

import app.user.UserIntegrationExtension;
import app.user.api.admin.AdminLoginRequest;
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
class AdminServiceTest extends UserIntegrationExtension {
    @Inject
    Repository<Admin> repository;

    @Inject
    AdminService adminService;

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
        AdminLoginRequest adminLoginRequest = new AdminLoginRequest();
        adminLoginRequest.name = "steve";
        adminLoginRequest.password = "1234";
        assertThat(adminService.login(adminLoginRequest)).isNotNull();
    }

    @AfterEach
    void delete() {
        repository.delete(admin.id);
    }
}