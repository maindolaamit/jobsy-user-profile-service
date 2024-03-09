package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Provided URL is not valid.")
public class InvalidUrlException extends AbstractWebExceptions {
    public InvalidUrlException(String url) {
        super(String.format("Provided URL:%s is not valid.", url), HttpStatus.BAD_REQUEST);
    }
}
