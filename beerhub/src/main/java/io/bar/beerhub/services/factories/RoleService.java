package io.bar.beerhub.services.factories;

import io.bar.beerhub.services.models.RoleServiceModel;

import java.util.List;

public interface RoleService {

    boolean seedRolesInDb();

    List<RoleServiceModel> findAllRoles();
}
