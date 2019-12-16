package io.bar.beerhub.services.factories;

import io.bar.beerhub.services.models.UserServiceModel;
import io.bar.beerhub.web.models.UserChangeRoleModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel register (UserServiceModel userServiceModel);

    List<UserServiceModel> getAllUsers();

    boolean addDelUserRole(UserChangeRoleModel userChangeRoleModel);
}
