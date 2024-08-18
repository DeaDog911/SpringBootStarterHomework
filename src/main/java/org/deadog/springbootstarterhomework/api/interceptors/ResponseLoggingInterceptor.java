package org.deadog.springbootstarterhomework.api.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deadog.springbootstarterhomework.config.LoggingProperties;
import org.deadog.springbootstarterhomework.util.HttpUtil;
import org.deadog.springbootstarterhomework.util.tags.AbstractTagsSetter;
import org.deadog.springbootstarterhomework.util.tags.LoggingAbstractTagsSetter;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

@RequiredArgsConstructor
public class ResponseLoggingInterceptor implements HandlerInterceptor {
    private static Logger log = LogManager.getLogger(RequestLoggingInterceptor.class);

    private final LoggingProperties loggingProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!loggingProperties.getLoggingMethods().contains(HttpMethod.valueOf(request.getMethod()))) {
            return true;
        }

        long startTime = Instant.now().toEpochMilli();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!loggingProperties.getLoggingMethods().contains(HttpMethod.valueOf(request.getMethod()))) {
            return;
        }

        AbstractTagsSetter tags = new LoggingAbstractTagsSetter(response, request);
        log.log(loggingProperties.getLogLevel(), tags.setTags(loggingProperties.getLogResponseFormat()));
    }
}
