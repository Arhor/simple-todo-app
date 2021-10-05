package dev.arhor.simple.todo.data.repository;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import dev.arhor.simple.todo.config.DatabaseConfig;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = NONE)
@Testcontainers(disabledWithoutDocker = true)
@ContextConfiguration(classes = {DatabaseConfig.class})
abstract class DatabaseIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:11.7");

    @DynamicPropertySource
    static void registerDynamicProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", db::getJdbcUrl);
        registry.add("spring.datasource.username", db::getUsername);
        registry.add("spring.datasource.password", db::getPassword);
    }
}
