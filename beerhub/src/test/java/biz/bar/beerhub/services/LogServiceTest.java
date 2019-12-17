package biz.bar.beerhub.services;

import io.bar.beerhub.data.models.Log;
import io.bar.beerhub.data.repositories.LogRepository;
import io.bar.beerhub.services.factories.LogService;
import io.bar.beerhub.services.models.LogServiceModel;
import io.bar.beerhub.services.services.LogServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {
    LogRepository logRepository;
    LogService logService;
    ModelMapper modelMapper;

    @Before
    public void before() {
        this.logRepository = Mockito.mock(LogRepository.class);
        this.logService = new LogServiceImpl(logRepository, new ModelMapper());
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void recLogInDb_withLog_ShouldReturnLogServiceModel() {
        LogServiceModel logForSave = new LogServiceModel();
        logForSave.setTime(LocalDateTime.now());
        logForSave.setUsername("Az");
        logForSave.setDescription("some important staff just happend");

        Log savedLog = this.modelMapper.map(logForSave, Log.class);

        when(logRepository.saveAndFlush(any(Log.class))).thenReturn(savedLog);

        LogServiceModel result = this.logService.recLogInDb(logForSave);

        Assert.assertThat(logForSave, samePropertyValuesAs(result));
    }

    @Test
    public void getAllLogsOrderByDate_whenEmptyDb_ShouldReturnEmptyList() {
        when(logRepository.findAllByOrderByTimeDesc()).thenReturn(new ArrayList<>());

        List<LogServiceModel> result = this.logService.getAllLogsOrderByDate();

        Assert.assertEquals(new ArrayList<>(), result);
    }

    @Test
    public void getAllLogsOrderByDate_whenLogsInDb_ShouldReturnListOrderedByDate() {
        Log logForSave = new Log();
        logForSave.setTime(LocalDateTime.now());
        logForSave.setUsername("Az");
        logForSave.setDescription("some important staff just happened");

        Log logForSave2 = new Log();
        logForSave2.setTime(LocalDateTime.now().minusDays(5));
        logForSave2.setUsername("Az");
        logForSave2.setDescription("even more important staff just happened");

        List<Log> savedLogs = new ArrayList<>();
        savedLogs.add(logForSave);
        savedLogs.add(logForSave2);

        savedLogs.sort((a, b) -> b.getTime().compareTo(a.getTime()));

        LogServiceModel logModelForSave = new LogServiceModel();
        logModelForSave.setTime(logForSave.getTime());
        logModelForSave.setUsername("Az");
        logModelForSave.setDescription("some important staff just happened");

        LogServiceModel logModelForSave2 = new LogServiceModel();
        logModelForSave2.setTime(logForSave2.getTime());
        logModelForSave2.setUsername("Az");
        logModelForSave2.setDescription("even more important staff just happened");

        List<LogServiceModel> savedModelLogs = new ArrayList<>();
        savedModelLogs.add(logModelForSave);
        savedModelLogs.add(logModelForSave2);

        savedModelLogs.sort((a, b) -> b.getTime().compareTo(a.getTime()));

        when(logRepository.findAllByOrderByTimeDesc()).thenReturn(savedLogs);

        List<LogServiceModel> result = this.logService.getAllLogsOrderByDate();

        Assert.assertThat(result.get(0), samePropertyValuesAs(savedModelLogs.get(0)));
        Assert.assertThat(result.get(1), samePropertyValuesAs(savedModelLogs.get(1)));
        Assert.assertEquals(result.size(), savedModelLogs.size());
    }
}
