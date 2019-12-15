package io.bar.beerhub.config;

import io.bar.beerhub.util.EscapeCharsUtilImpl;
import io.bar.beerhub.util.FileUtilImpl;
import io.bar.beerhub.util.factory.EscapeCharsUtil;
import io.bar.beerhub.util.factory.FileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public EscapeCharsUtil escapeCharsUtil() {return new EscapeCharsUtilImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }
}
