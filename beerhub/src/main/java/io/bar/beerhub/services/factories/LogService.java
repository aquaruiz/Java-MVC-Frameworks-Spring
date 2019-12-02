package io.bar.beerhub.services.factories;

import io.bar.beerhub.services.models.LogServiceModel;

import java.util.List;

public interface LogService {
    LogServiceModel recLogInDb(LogServiceModel logServiceModel);

    List<LogServiceModel> getAllLogsOrderByDate();
}
