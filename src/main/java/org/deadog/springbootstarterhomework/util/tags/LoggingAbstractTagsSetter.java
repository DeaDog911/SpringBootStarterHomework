package org.deadog.springbootstarterhomework.util.tags;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.deadog.springbootstarterhomework.util.HttpUtil;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.time.Instant;
import java.util.Enumeration;
import java.util.Locale;

public class LoggingAbstractTagsSetter extends AbstractTagsSetter {
    private String httpMethod;

    private String url;

    private String locale;

    private String headers;

    private String queryString;

    private String responseCode;

    private String processingTime;

    public LoggingAbstractTagsSetter(HttpServletRequest request) {
        this.httpMethod = request.getMethod();
        this.url = request.getRequestURL().toString();
        this.locale = request.getLocale().toString();
        this.headers = HttpUtil.getHeadersAsString(request);
        this.queryString = request.getQueryString();
    }

    public LoggingAbstractTagsSetter(HttpServletResponse response, HttpServletRequest request) {
        this.httpMethod = request.getMethod();
        this.url = request.getRequestURL().toString();
        this.locale = request.getLocale().toString();
        this.headers = HttpUtil.getHeadersAsString(response);
        this.queryString = request.getQueryString();
        this.responseCode = String.valueOf(response.getStatus());

        Long startTime = (Long) request.getAttribute("startTime");
        if (startTime != null) {
            processingTime = String.valueOf(Instant.now().toEpochMilli() - startTime);
        }
    }

    @Override
    protected StandardEvaluationContext getEvaluationContext() {
        StandardEvaluationContext tags = new StandardEvaluationContext();
        tags.setVariable("method", httpMethod);
        tags.setVariable("url", url);
        tags.setVariable("locale", locale);
        tags.setVariable("headers", headers);
        tags.setVariable("queryString", queryString);
        tags.setVariable("responseCode", responseCode);
        tags.setVariable("processingTime", processingTime);
        return tags;
    }
}
