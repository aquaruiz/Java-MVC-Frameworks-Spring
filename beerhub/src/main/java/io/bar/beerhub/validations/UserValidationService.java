package io.bar.beerhub.validations;

import io.bar.beerhub.data.models.User;
import io.bar.beerhub.services.models.UserServiceModel;

public interface UserValidationService {
    boolean isValid(User user);

    boolean isValid(UserServiceModel userServiceModel);
}
