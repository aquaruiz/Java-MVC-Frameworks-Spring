package io.bar.beerhub.scheduled;

import io.bar.beerhub.data.models.Log;
import io.bar.beerhub.data.repositories.LogRepository;
import io.bar.beerhub.util.factory.FileUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class ScheduledJobImpl implements ScheduledJob {
    private final LogRepository logRepository;
    private final FileUtil fileUtil;

    public ScheduledJobImpl(LogRepository logRepository, FileUtil fileUtil) {
        this.logRepository = logRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    @Scheduled(fixedRate = 5000)
    public void scheduledJob() throws IOException {
        List<Log> logs = this.logRepository.findAll();

        this.fileUtil.writeFile("files/log.txt", logs);
    }
}
