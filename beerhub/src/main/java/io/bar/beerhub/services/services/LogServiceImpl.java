package io.bar.beerhub.services.services;

import io.bar.beerhub.data.models.Log;
import io.bar.beerhub.data.repositories.LogRepository;
import io.bar.beerhub.services.factories.LogService;
import io.bar.beerhub.services.models.LogServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogServiceModel recLogInDb(LogServiceModel logServiceModel) {
        Log log = this.modelMapper.map(logServiceModel, Log.class);
        Log savedLog = this.logRepository.saveAndFlush(log);
        return this.modelMapper.map(savedLog, LogServiceModel.class);
    }
}
