package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Role;
import io.bar.beerhub.data.repositories.RoleRepository;
import io.bar.beerhub.services.factories.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0){
            this.roleRepository.saveAndFlush(new Role("ROLE_ROOT"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_BARTENDER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_CUSTOMER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_WAITRESS"));
        }
    }
}