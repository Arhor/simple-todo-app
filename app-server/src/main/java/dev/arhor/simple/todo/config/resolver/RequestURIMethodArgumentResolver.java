package dev.arhor.simple.todo.config.resolver;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestURIMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Class<HttpServletRequest> NATIVE_REQUEST_TYPE = HttpServletRequest.class;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return URI.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
        final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory
    ) {
        var httpServletRequest = webRequest.getNativeRequest(NATIVE_REQUEST_TYPE);
        if (httpServletRequest != null) {
            return URI.create(httpServletRequest.getRequestURI());
        }
        throw new IllegalStateException(
            "Unsupported request type, required: %s, provided: null".formatted(
                NATIVE_REQUEST_TYPE.getSimpleName()
            )
        );
    }
}
