package org.deadog.springbootstarterhomework.exceptions;

public class LoggingStartupException extends RuntimeException {
    public LoggingStartupException(String message) {
        super(message);
    }

    public LoggingStartupException(String message, String... args) {
        super(String.format(message, args));
    }
}
