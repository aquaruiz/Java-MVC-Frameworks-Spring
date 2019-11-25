package io.aquariuz.beerhub.services.factories;

import io.aquariuz.beerhub.services.models.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    void seedRolesInDb();
    Set<RoleServiceModel> findAllRoles();
    RoleServiceModel findByAuthority(String role);
}
