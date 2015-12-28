package com.bank;

import java.util.HashMap;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author SARAT
 */
@Configuration
@ComponentScan(basePackages = {"com.bank.session", "com.bank.beans","com.bank.controller"})
public class TestContext {

    private static final String MESSAGE_SOURCE_BASE_NAME = "messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer scopeConfigurer = new CustomScopeConfigurer();

        HashMap<String, Object> scopes = new HashMap<>();
       
        scopes.put(WebApplicationContext.SCOPE_SESSION,
                new SimpleThreadScope());
        scopeConfigurer.setScopes(scopes);

        return scopeConfigurer;
    }
}
