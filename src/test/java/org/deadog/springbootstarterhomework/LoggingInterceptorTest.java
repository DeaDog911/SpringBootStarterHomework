package org.deadog.springbootstarterhomework;

import org.apache.logging.log4j.Level;
import org.deadog.springbootstarterhomework.api.interceptors.RequestLoggingInterceptor;
import org.deadog.springbootstarterhomework.api.interceptors.ResponseLoggingInterceptor;
import org.deadog.springbootstarterhomework.config.LoggingProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class LoggingInterceptorTest {
    @Mock
    private LoggingProperties loggingProperties;

    private RequestLoggingInterceptor requestLoggingInterceptor;
    private ResponseLoggingInterceptor responseLoggingInterceptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requestLoggingInterceptor = new RequestLoggingInterceptor(loggingProperties);
        responseLoggingInterceptor = new ResponseLoggingInterceptor(loggingProperties);
    }

    @Test
    void testRequestLoggingInterceptor_whenLoggingEnabled_shouldLog() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");

        when(loggingProperties.getLogLevel()).thenReturn(Level.INFO);
        when(loggingProperties.getLoggingMethods()).thenReturn(List.of(HttpMethod.GET));
        when(loggingProperties.getLogRequestFormat()).thenReturn("Request: Method = #{#method}");

        boolean result = requestLoggingInterceptor.preHandle(request, new MockHttpServletResponse(), null);

        assertTrue(result);
        verify(loggingProperties).getLogRequestFormat();
        verify(loggingProperties).getLogLevel();
    }

    @Test
    void testRequestLoggingInterceptor_whenMethodNotLogged_shouldNotLog() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");

        when(loggingProperties.getLogLevel()).thenReturn(Level.INFO);
        when(loggingProperties.getLoggingMethods()).thenReturn(List.of(HttpMethod.GET));
        when(loggingProperties.getLogRequestFormat()).thenReturn("Request: Method = #{#method}");

        boolean result = requestLoggingInterceptor.preHandle(request, new MockHttpServletResponse(), null);

        assertTrue(result);
        verify(loggingProperties, never()).getLogRequestFormat();
        verify(loggingProperties, never()).getLogLevel();
    }

    @Test
    void testResponseLoggingInterceptor_whenLoggingEnabled_shouldLog() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setMethod("GET");

        when(loggingProperties.getLogLevel()).thenReturn(Level.INFO);
        when(loggingProperties.getLoggingMethods()).thenReturn(List.of(HttpMethod.GET));
        when(loggingProperties.getLogResponseFormat()).thenReturn("Response: Code = #{#responseCode}");

        responseLoggingInterceptor.preHandle(request, response, null);
        responseLoggingInterceptor.afterCompletion(request, response, null, null);

        verify(loggingProperties).getLogResponseFormat();
        verify(loggingProperties).getLogLevel();
    }

    @Test
    void testResponseLoggingInterceptor_whenMethodNotLogging_shouldNotLog() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setMethod("GET");

        when(loggingProperties.getLogLevel()).thenReturn(Level.INFO);
        when(loggingProperties.getLoggingMethods()).thenReturn(List.of(HttpMethod.POST));
        when(loggingProperties.getLogResponseFormat()).thenReturn("Response: Code = #{#responseCode}");

        responseLoggingInterceptor.preHandle(request, response, null);
        responseLoggingInterceptor.afterCompletion(request, response, null, null);

        verify(loggingProperties, never()).getLogResponseFormat();
        verify(loggingProperties, never()).getLogLevel();
    }

}
