package org.deadog.springbootstarterhomework;

import org.deadog.springbootstarterhomework.exceptions.LoggingStartupException;
import org.deadog.springbootstarterhomework.init.LoggingFailureAnalyzer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = "my-logging.enabled=true")
class LoggingFailureAnalyzerTest {
    static class TestLoggingFailureAnalyzer extends LoggingFailureAnalyzer {
        @Override
        public FailureAnalysis analyze(Throwable rootFailure, LoggingStartupException cause) {
            return super.analyze(rootFailure, cause);
        }
    }

    @Test
    void testAnalyze() {
        String expectedMessage = "Logging startup failed";
        String expectedAction = "Укажите валидные значения для свойства";

        LoggingStartupException mockException = mock(LoggingStartupException.class);
        when(mockException.getMessage()).thenReturn(expectedMessage);

        TestLoggingFailureAnalyzer analyzer = new TestLoggingFailureAnalyzer();

        FailureAnalysis analysis = analyzer.analyze(new RuntimeException(), mockException);

        assertEquals(expectedMessage, analysis.getDescription());
        assertEquals(expectedAction, analysis.getAction());
        assertEquals(mockException, analysis.getCause());
    }
}