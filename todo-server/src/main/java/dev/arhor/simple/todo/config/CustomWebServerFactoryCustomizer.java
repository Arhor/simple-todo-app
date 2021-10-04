package dev.arhor.simple.todo.config;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

@Component
public class CustomWebServerFactoryCustomizer implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    private static final boolean IS_DIRECT_BUFFER = false;
    private static final int BYTE_BUFFER_SIZE = 1024;
    private static final String WEB_SOCKET_DEPLOYMENT_INFO = "io.undertow.websockets.jsr.WebSocketDeploymentInfo";

    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers(this::customizeByteBuffer);
    }

    private void customizeByteBuffer(DeploymentInfo deploymentInfo) {
        var byteBufferPool = new DefaultByteBufferPool(IS_DIRECT_BUFFER, BYTE_BUFFER_SIZE);
        var webSocketDeploymentInfo = new WebSocketDeploymentInfo().setBuffers(byteBufferPool);
        deploymentInfo.addServletContextAttribute(WEB_SOCKET_DEPLOYMENT_INFO, webSocketDeploymentInfo);
    }
}
