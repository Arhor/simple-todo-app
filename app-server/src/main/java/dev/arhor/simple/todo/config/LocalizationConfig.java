package dev.arhor.simple.todo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration(proxyBeanMethods = false)
public class LocalizationConfig {

    public static final String ERROR_MESSAGES_BEAN = "errorMessages";

    @Bean(ERROR_MESSAGES_BEAN)
    public MessageSource errorMessages() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:error-messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator(@Qualifier(ERROR_MESSAGES_BEAN) final MessageSource errorMessages) {
        var validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(errorMessages);
        return validatorFactoryBean;
    }
}
