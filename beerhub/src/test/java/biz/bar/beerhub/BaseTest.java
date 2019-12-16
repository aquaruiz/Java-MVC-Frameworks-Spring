package biz.bar.beerhub;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class BaseTest {

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);
        this.before();
    }

    protected void before() {
    }
}
