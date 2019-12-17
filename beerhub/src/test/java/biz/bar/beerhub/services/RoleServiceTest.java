package biz.bar.beerhub.services;

import io.bar.beerhub.data.models.Role;
import io.bar.beerhub.data.repositories.RoleRepository;
import io.bar.beerhub.services.factories.RoleService;
import io.bar.beerhub.services.models.RoleServiceModel;
import io.bar.beerhub.services.services.RoleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {
    RoleRepository roleRepository;
    RoleService roleService;
    ModelMapper modelMapper;

    @Before
    public void before() {
        this.roleRepository = Mockito.mock(RoleRepository.class);
        this.roleService = new RoleServiceImpl(roleRepository, new ModelMapper());
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void roleRepository_withNoRolesInDb_ShouldSeedRoles() {
        when(roleRepository.count()).thenReturn(0l);

        boolean result = roleService.seedRolesInDb();

        Assert.assertEquals(true, result);
    }

    @Test
    public void roleRepository_withRolesInDb_ShouldNOTSeedRoles() {
        when(roleRepository.count()).thenReturn(5l);

        boolean result = roleService.seedRolesInDb();

        Assert.assertEquals(false, result);
    }

    @Test
    public void roleRepository_findAllRoles_ShouldReturnAllRolesInDbExceptROOT() {
        Role userRole = new Role() {{
            setAuthority("ROLE_USER");
        }};
        Role customerRole = new Role() {{
            setAuthority("ROLE_CUSTOMER");
        }};
        Role rootRole = new Role() {{
            setAuthority("ROLE_ROOT");
        }};

        List<Role> rolesInDb = new ArrayList<>(){{
            add(customerRole);
            add(userRole);
            add(rootRole);
        }};

        List<RoleServiceModel> extractedRoles = rolesInDb
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .filter(r -> !r.getAuthority().equals("ROLE_ROOT"))
                .collect(Collectors.toUnmodifiableList());

        when(this.roleRepository.findAll()).thenReturn(rolesInDb);

        List<RoleServiceModel> result = this.roleService.findAllRoles();

        Assert.assertEquals(extractedRoles.size(), result.size());
    }
}
