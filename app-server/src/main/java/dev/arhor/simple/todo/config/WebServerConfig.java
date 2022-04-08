package dev.arhor.simple.todo.config;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration(proxyBeanMethods = false)
public class WebServerConfig implements WebMvcConfigurer {

    private static final String[] RESOURCE_PATH_PATTERNS = {
        "/index.html",
        "/assets/**"
    };

    private static final String[] RESOURCE_CLASSPATH_LOCATIONS = {
        "classpath:/static/",
        "classpath:/static/assets/",
    };

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/{path:[^\\.]*}").setViewName("forward:/");
    }

    @Override
    public void configurePathMatch(final PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCE_PATH_PATTERNS).addResourceLocations(RESOURCE_CLASSPATH_LOCATIONS);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(final MethodParameter parameter) {
                return URI.class.equals(parameter.getParameterType());
            }

            @Override
            public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
                return URI.create(
                    webRequest.getNativeRequest(HttpServletRequest.class)
                              .getRequestURI()
                );
            }
        });
    }

    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        for (var converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jackson2HttpMessageConverter) {
                var objectMapper = jackson2HttpMessageConverter.getObjectMapper();
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            }
        }
    }
}
