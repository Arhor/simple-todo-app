package dev.arhor.simple.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll()
            .and()
            .oauth2Login()
            .and()
            .headers()
            .xssProtection()
            .and()
            .contentSecurityPolicy("script-src 'self'");
    }
}
