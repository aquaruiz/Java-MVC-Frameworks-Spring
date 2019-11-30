package io.bar.beerhub.services.factories;

import io.bar.beerhub.services.models.RoleServiceModel;

import java.util.List;

public interface RoleService {

    void seedRolesInDb();

    List<RoleServiceModel> findAllRoles();
}
