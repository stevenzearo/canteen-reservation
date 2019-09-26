package app.user.service;

import app.user.api.admin.BOAdminLoginRequest;
import app.user.api.admin.BOAdminLoginResponse;
import app.user.domain.Admin;
import core.framework.crypto.Hash;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.UnauthorizedException;

import java.util.List;

/**
 * @author steve
 */
public class BOAdminService {
    @Inject
    Repository<Admin> repository;

    public BOAdminLoginResponse login(BOAdminLoginRequest request) {
        List<Admin> adminList = repository.select("name = ?", request.name);
        BOAdminLoginResponse BOAdminLoginResponse;
        if (adminList == null || adminList.size() != 1 || !adminList.get(0).password.equals(Hash.sha256Hex(request.password))) {
            throw new UnauthorizedException("Admin name or password incorrect");
        } else {
            BOAdminLoginResponse = new BOAdminLoginResponse();
            BOAdminLoginResponse.id = adminList.get(0).id;
            BOAdminLoginResponse.name = request.name;
        }
        return BOAdminLoginResponse;
    }
}
