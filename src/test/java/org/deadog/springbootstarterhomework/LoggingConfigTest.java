package org.deadog.springbootstarterhomework;

import org.deadog.springbootstarterhomework.api.interceptors.RequestLoggingInterceptor;
import org.deadog.springbootstarterhomework.api.interceptors.ResponseLoggingInterceptor;
import org.deadog.springbootstarterhomework.config.LoggingConfig;
import org.deadog.springbootstarterhomework.config.LoggingProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class LoggingConfigTest {
    @MockBean
    private LoggingProperties loggingProperties;

    @MockBean
    private ApplicationContext context;

    @InjectMocks
    private LoggingConfig loggingConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loggingConfig = new LoggingConfig(loggingProperties, context);
    }

    @Test
    void testAddInterceptors_whenBeansExist_shouldAddInterceptors() {
        InterceptorRegistry registry = mock(InterceptorRegistry.class);

        when(context.containsBean("requestLoggingInterceptor")).thenReturn(true);
        when(context.containsBean("responseLoggingInterceptor")).thenReturn(true);

        loggingConfig.addInterceptors(registry);

        verify(registry, times(1)).addInterceptor(any(RequestLoggingInterceptor.class));
        verify(registry, times(1)).addInterceptor(any(ResponseLoggingInterceptor.class));
    }

    @Test
    void testAddInterceptors_whenBeansDoesNotExist_shouldNotAddInterceptors() {
        InterceptorRegistry registry = mock(InterceptorRegistry.class);

        when(context.containsBean("requestLoggingInterceptor")).thenReturn(false);
        when(context.containsBean("responseLoggingInterceptor")).thenReturn(false);

        loggingConfig.addInterceptors(registry);

        verify(registry, never()).addInterceptor(any(RequestLoggingInterceptor.class));
        verify(registry, never()).addInterceptor(any(ResponseLoggingInterceptor.class));
    }

    @Test
    void testAddInterceptors_whenOneBeanExistsOneDoesNotExist_shouldAddOneAndOneNot() {
        InterceptorRegistry registry = mock(InterceptorRegistry.class);

        when(context.containsBean("requestLoggingInterceptor")).thenReturn(false);
        when(context.containsBean("responseLoggingInterceptor")).thenReturn(true);

        loggingConfig.addInterceptors(registry);

        verify(registry, never()).addInterceptor(any(RequestLoggingInterceptor.class));
        verify(registry, times(1)).addInterceptor(any(ResponseLoggingInterceptor.class));
    }
}
