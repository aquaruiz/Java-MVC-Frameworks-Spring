package io.bar.beerhub.validations;

import io.bar.beerhub.data.models.User;
import io.bar.beerhub.services.models.UserServiceModel;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isValid(User user) {
        return user != null;
    }

    @Override
    public boolean isValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }
}
