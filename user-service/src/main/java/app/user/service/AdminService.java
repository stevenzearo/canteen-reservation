package app.user.service;

import app.user.api.admin.AdminLoginRequest;
import app.user.api.admin.AdminLoginResponse;
import app.user.api.admin.LoginStatus;
import app.user.domain.Admin;
import core.framework.crypto.Hash;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

/**
 * @author steve
 */
public class AdminService {
    @Inject
    Repository<Admin> repository;

    public AdminLoginResponse login(AdminLoginRequest request) {
        Admin admin = repository.get(request.name).orElseThrow(() -> new NotFoundException(Strings.format("Admin not found name = {}", request.name)));
        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.status = admin.password.equals(Hash.sha256Hex(request.password)) ? LoginStatus.SUCCESS : LoginStatus.FAILED;
        return adminLoginResponse;

    }
}
