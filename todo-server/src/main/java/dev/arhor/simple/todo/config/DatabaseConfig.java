package dev.arhor.simple.todo.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import dev.arhor.simple.todo.SpringProfile;
import dev.arhor.simple.todo.data.model.DomainObject;
import dev.arhor.simple.todo.service.TimeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableJdbcAuditing(modifyOnCreate = false, dateTimeProviderRef = DatabaseConfig.DATE_TIME_PROVIDER_BEAN)
@EnableJdbcRepositories(basePackages = "dev.arhor.simple.todo.data.*")
@EnableTransactionManagement
public class DatabaseConfig {

    public static final String DATE_TIME_PROVIDER_BEAN = "InstantDateTimeProviderUTC";

    @Bean(DATE_TIME_PROVIDER_BEAN)
    public DateTimeProvider dateTimeProvider(TimeService timeService) {
        return () -> Optional.of(timeService.now());
    }

    @Bean
    @Profile(SpringProfile.DEVELOPMENT)
    public BeforeSaveCallback<DomainObject<?>> beforeSaveCallback() {
        return (aggregate, aggregateChange) -> {
            log.debug("before save: {}", aggregate);
            return aggregate;
        };
    }

    @Bean
    @Profile(SpringProfile.DEVELOPMENT)
    public AfterSaveCallback<DomainObject<?>> afterSaveCallback() {
        return aggregate -> {
            log.debug("after save: {}", aggregate);
            return aggregate;
        };
    }
}
