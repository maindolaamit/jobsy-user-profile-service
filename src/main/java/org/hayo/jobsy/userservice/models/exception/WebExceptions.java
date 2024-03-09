package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;

public interface WebExceptions {
    String getReason();
    HttpStatus getStatus();
}
