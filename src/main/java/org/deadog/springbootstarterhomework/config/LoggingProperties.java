package org.deadog.springbootstarterhomework.config;

import lombok.Data;
import org.apache.logging.log4j.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "my-logging")
public class LoggingProperties {
    private Boolean enabled;

    private Boolean logRequests;

    private Boolean logResponses;

    private List<HttpMethod> loggingMethods = Arrays.asList(HttpMethod.values());

    private Level logLevel = Level.INFO;

    private String logRequestFormat = "Request: " +
            "Method = #{#method}, " +
            "URL = #{#url}, " +
            "Locale = #{#locale}, " +
            "Headers = #{#headers}, " +
            "Query Params: #{#queryString}";

    private String logResponseFormat = "Response: " +
            "Code = #{#responseCode}, " +
            "Headers = #{#headers}, " +
            "Locale = #{#locale}, " +
            "Processing time = #{#processingTime} ms";
}
