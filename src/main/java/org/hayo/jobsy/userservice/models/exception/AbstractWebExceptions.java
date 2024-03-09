package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractWebExceptions extends RuntimeException implements WebExceptions {
    private final String reason;
    private final HttpStatus status;

    public AbstractWebExceptions(String message, HttpStatus status) {
        super(message);
        this.reason = message;
        this.status = status;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
