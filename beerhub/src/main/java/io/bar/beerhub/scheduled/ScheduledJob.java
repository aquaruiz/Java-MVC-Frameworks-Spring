package io.bar.beerhub.scheduled;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.FileNotFoundException;
import java.io.IOException;

@EnableAsync
public interface ScheduledJob {
    @Async
    void scheduledJob() throws IOException;
}
