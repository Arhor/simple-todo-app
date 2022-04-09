package dev.arhor.simple.todo.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("configuration.resources")
public record ResourcesConfigurationProperties(String[] patterns, String[] locations) {

    @Override
    public String[] patterns() {
        return this.patterns.clone();
    }

    @Override
    public String[] locations() {
        return this.locations.clone();
    }
}
