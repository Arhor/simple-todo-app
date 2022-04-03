package dev.arhor.simple.todo.config;

import static dev.arhor.simple.todo.common.TimeUtils.DEFAULT_ZONE_ID;
import static dev.arhor.simple.todo.common.TimeUtils.TIME_MEASUREMENT_ACCURACY;

import java.time.Clock;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableJdbcAuditing(modifyOnCreate = false, dateTimeProviderRef = "instantDateTimeProviderUTC")
@EnableJdbcRepositories(basePackages = "dev.arhor.simple.todo.data.*")
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    public DateTimeProvider instantDateTimeProviderUTC() {
        return () -> Optional.of(
            Clock.system(DEFAULT_ZONE_ID)
                 .instant()
                 .truncatedTo(TIME_MEASUREMENT_ACCURACY)
        );
    }
}
