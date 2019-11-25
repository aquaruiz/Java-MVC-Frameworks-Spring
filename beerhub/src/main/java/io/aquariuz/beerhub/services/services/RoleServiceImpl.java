package io.aquariuz.beerhub.services.services;

import io.aquariuz.beerhub.data.models.Role;
import io.aquariuz.beerhub.data.repositories.RoleRepository;
import io.aquariuz.beerhub.services.factories.RoleService;
import io.aquariuz.beerhub.services.models.RoleServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String role) {
        return this.modelMapper.map(
                this.roleRepository.findByAuthority(role),
                RoleServiceModel.class
        );
    }
}