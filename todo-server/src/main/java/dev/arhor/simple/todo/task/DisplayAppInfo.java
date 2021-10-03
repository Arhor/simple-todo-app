package dev.arhor.simple.todo.task;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DisplayAppInfo implements StartupTask {

    private static final String DEFAULT_HOST = "localhost";
    private static final String APP_INFO_TEMPLATE = """
            
            --------------------------------------------------------------------------------
             Application `{}` is running! Access URLs:
             - Local:     {}://{}:{}{}
             - External:  {}://{}:{}{}
             - java ver.: {}
            --------------------------------------------------------------------------------
            """;

    private final Environment env;

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public void execute() {
        log.info(APP_INFO_TEMPLATE, prepareTemplateParams());
    }

    private Object[] prepareTemplateParams() {
        var appName = getAppName();
        var serverPort = getServerPort();
        var contextPath = getContextPath();
        var protocol = getProtocol();
        var hostAddress = getHostAddress();
        var javaVersion = getJavaVersion();

        return new Object[]{
                appName,
                protocol,
                DEFAULT_HOST,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                javaVersion
        };
    }

    private String getAppName() {
        return env.getProperty("spring.application.name");
    }

    private String getServerPort() {
        return env.getProperty("server.port");
    }

    private String getContextPath() {
        var contextPath = env.getProperty("server.servlet.context-path");
        return ((contextPath != null) && !contextPath.isBlank()) ? contextPath : "/";
    }

    private String getProtocol() {
        return (env.getProperty("server.ssl.key-store") == null) ? "http" : "https";
    }

    private String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `{}` as fallback", DEFAULT_HOST);
            return DEFAULT_HOST;
        }
    }

    private String getJavaVersion() {
        return System.getProperty("java.version");
    }
}
