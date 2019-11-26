package io.bar.beerhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/register", "/login").permitAll()
                .antMatchers("/static/**", "/css/**", "/js/**", "/img/**").permitAll()
                .anyRequest().authenticated()
//                .antMatchers("/bartender/**").hasRole("BARTENDER") //??
//                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .and()
                .logout().logoutSuccessUrl("/?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized");

        http.headers().disable();
    }
}