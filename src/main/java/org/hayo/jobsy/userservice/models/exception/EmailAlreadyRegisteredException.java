package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyRegisteredException extends AbstractWebExceptions {
    public EmailAlreadyRegisteredException(String email) {
        super(String.format("Provided email:%s is already registered.", email), HttpStatus.CONFLICT);
    }
}
