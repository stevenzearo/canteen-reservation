package app.user.service;

import app.user.api.admin.AdminLoginRequest;
import app.user.api.admin.AdminLoginResponse;
import app.user.api.user.LoginStatus;
import app.user.domain.Admin;
import core.framework.crypto.Hash;
import core.framework.db.Repository;
import core.framework.inject.Inject;

import java.util.List;

/**
 * @author steve
 */
public class AdminService {
    @Inject
    Repository<Admin> repository;

    public AdminLoginResponse login(AdminLoginRequest request) {
        List<Admin> adminList = repository.select("name = ?", request.name);
        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.status = LoginStatus.FAILED;
        if (adminList != null && adminList.size() == 1 && adminList.get(0).password.equals(Hash.sha256Hex(request.password))) {
            adminLoginResponse.status = LoginStatus.SUCCESS;
        }
        return adminLoginResponse;

    }
}
