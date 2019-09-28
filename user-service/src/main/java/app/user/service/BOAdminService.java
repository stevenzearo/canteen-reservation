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
        BOAdminLoginResponse response;
        if (!adminList.isEmpty() && adminList.get(0).password.equals(Hash.sha256Hex(request.password))) {
            throw new UnauthorizedException("admin name or password incorrect");
        } else {
            response = new BOAdminLoginResponse();
            response.id = adminList.get(0).id;
            response.name = request.name;
        }
        return response;
    }
}
