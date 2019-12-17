package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Role;
import io.bar.beerhub.data.repositories.RoleRepository;
import io.bar.beerhub.services.factories.RoleService;
import io.bar.beerhub.services.models.RoleServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public boolean seedRolesInDb() {
        if (this.roleRepository.count() == 0){
            this.roleRepository.saveAndFlush(new Role("ROLE_ROOT"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_BARTENDER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_CUSTOMER"));
            this.roleRepository.saveAndFlush(new Role("ROLE_WAITRESS"));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<RoleServiceModel> findAllRoles() {
        List<Role> roles = this.roleRepository.findAll();
        roles = roles.stream()
                .filter(r -> !r.getAuthority().equals("ROLE_ROOT"))
                .collect(Collectors.toList());
        return roles.stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }
}