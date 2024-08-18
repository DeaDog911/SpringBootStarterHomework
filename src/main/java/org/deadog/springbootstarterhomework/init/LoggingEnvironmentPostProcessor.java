package org.deadog.springbootstarterhomework.init;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deadog.springbootstarterhomework.config.LoggingProperties;
import org.deadog.springbootstarterhomework.exceptions.LoggingStartupException;
import org.deadog.springbootstarterhomework.util.StringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.lang.reflect.Field;

public class LoggingEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private static Logger log = LogManager.getLogger(LoggingEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("LoggingEnvironmentPostProcessor");

        Class<LoggingProperties> loggingProperties = LoggingProperties.class;
        String prefix = loggingProperties.getAnnotation(ConfigurationProperties.class).prefix();

        for (Field loggingProperty : loggingProperties.getDeclaredFields()) {
            if (loggingProperty.getType() != Boolean.class)
                continue;
            String propertyName = prefix + "." + StringUtil.camelToSnake(loggingProperty.getName());
            String enablePropertyValue = environment.getProperty(propertyName);
            if (enablePropertyValue != null && !isBoolean(enablePropertyValue)) {
                throw new LoggingStartupException("Ошибка при проверке свойства '%s'", propertyName);
            }
        }
    }

    private boolean isBoolean(String value) {
        return Boolean.TRUE.toString().equalsIgnoreCase(value)
                || Boolean.FALSE.toString().equalsIgnoreCase(value);
    }
}
