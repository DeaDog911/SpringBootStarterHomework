package org.deadog.springbootstarterhomework.config;

import lombok.RequiredArgsConstructor;
import org.deadog.springbootstarterhomework.api.interceptors.RequestLoggingInterceptor;
import org.deadog.springbootstarterhomework.api.interceptors.ResponseLoggingInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "my-logging", name = "enabled", havingValue = "true")
public class LoggingConfig implements WebMvcConfigurer {
    private final LoggingProperties loggingProperties;

    private final ApplicationContext context;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (context.containsBean("requestLoggingInterceptor")) {
            registry.addInterceptor(requestLoggingInterceptor());
        }
        if (context.containsBean("responseLoggingInterceptor")) {
            registry.addInterceptor(responseLoggingInterceptor());
        }
    }

    @Bean
    @ConditionalOnProperty(prefix = "my-logging", name = "log-requests", havingValue = "true")
    public RequestLoggingInterceptor requestLoggingInterceptor() {
        return new RequestLoggingInterceptor(loggingProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "my-logging", name = "log-responses", havingValue = "true")
    public ResponseLoggingInterceptor responseLoggingInterceptor() {
        return new ResponseLoggingInterceptor(loggingProperties);
    }
}
