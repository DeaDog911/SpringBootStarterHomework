package org.deadog.springbootstarterhomework.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Enumeration;

public class HttpUtil {
    public static String getHeadersAsString(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.append(headerName).append("=").append(headerValue).append("; ");
        }

        return headers.toString();
    }

    public static String getHeadersAsString(HttpServletResponse response) {
        StringBuilder headers = new StringBuilder();

        for (String headerName : response.getHeaderNames()) {
            String headerValue = response.getHeader(headerName);
            headers.append(headerName).append("=").append(headerValue).append("; ");
        }

        return headers.toString();
    }
}
