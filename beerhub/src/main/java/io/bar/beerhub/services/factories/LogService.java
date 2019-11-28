package io.bar.beerhub.services.factories;

import io.bar.beerhub.services.models.LogServiceModel;

public interface LogService {
    LogServiceModel recLogInDb(LogServiceModel logServiceModel);

}
