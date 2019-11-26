package io.bar.beerhub.services.factories;

import io.bar.beerhub.data.models.User;
import io.bar.beerhub.services.models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register (UserServiceModel userServiceModel);
}
