package io.aquariuz.beerhub.config;

import io.aquariuz.beerhub.util.FileUtilImpl;
import io.aquariuz.beerhub.util.ValidatorUtilImpl;
import io.aquariuz.beerhub.util.XmlParserImpl;
import io.aquariuz.beerhub.util.factory.FileUtil;
import io.aquariuz.beerhub.util.factory.ValidatorUtil;
import io.aquariuz.beerhub.util.factory.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public FileUtil fileUtil() { return new FileUtilImpl(); }

    @Bean
    public XmlParser xmlParser() { return new XmlParserImpl(); }

    @Bean
    public ValidatorUtil validatorUtil() { return new ValidatorUtilImpl(); }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
