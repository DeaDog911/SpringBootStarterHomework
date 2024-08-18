package org.deadog.springbootstarterhomework;

import org.deadog.springbootstarterhomework.exceptions.LoggingStartupException;
import org.deadog.springbootstarterhomework.init.LoggingEnvironmentPostProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class LoggingEnvironmentPostProcessorTest {
    @Test
    void testPostProcessor_whenInvalidBooleanProperty_shouldThrowException() {
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("my-logging.log-requests", "invalid_boolean");

        LoggingEnvironmentPostProcessor processor = new LoggingEnvironmentPostProcessor();
        assertThrows(LoggingStartupException.class, () -> {
            processor.postProcessEnvironment(environment, new SpringApplication());
        });
    }

    @Test
    void testPostProcessor_whenCorrectBooleanProperty_shouldNotThrowException() {
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("my-logging.log-requests", "true");

        LoggingEnvironmentPostProcessor processor = new LoggingEnvironmentPostProcessor();
        assertDoesNotThrow(() -> processor.postProcessEnvironment(environment, new SpringApplication()));
    }
}
