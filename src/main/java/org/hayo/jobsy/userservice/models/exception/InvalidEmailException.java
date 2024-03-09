package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailException extends AbstractWebExceptions {
    public InvalidEmailException(String email) {
        super(String.format("Provided email:%s is not valid.", email), HttpStatus.BAD_REQUEST);
    }
}
