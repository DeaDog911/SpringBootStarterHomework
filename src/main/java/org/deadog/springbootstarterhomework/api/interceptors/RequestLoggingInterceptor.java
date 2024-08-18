package org.deadog.springbootstarterhomework.api.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deadog.springbootstarterhomework.config.LoggingProperties;
import org.deadog.springbootstarterhomework.util.tags.AbstractTagsSetter;
import org.deadog.springbootstarterhomework.util.tags.LoggingAbstractTagsSetter;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class RequestLoggingInterceptor implements HandlerInterceptor {
    private static Logger log = LogManager.getLogger(RequestLoggingInterceptor.class);

    private final LoggingProperties loggingProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!loggingProperties.getLoggingMethods().contains(HttpMethod.valueOf(request.getMethod()))) {
            return true;
        }

        AbstractTagsSetter tags = new LoggingAbstractTagsSetter(request);
        log.log(loggingProperties.getLogLevel(), tags.setTags(loggingProperties.getLogRequestFormat()));
        return true;
    }
}
