package io.bar.beerhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BeerhubApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication
                .run(BeerhubApplication.class, args);
    }

}
