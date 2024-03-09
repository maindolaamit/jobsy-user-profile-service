package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;

public class UserEmailNotExistsException extends AbstractWebExceptions {

    public UserEmailNotExistsException(String email) {
        super("User email not registered: " + email, HttpStatus.NOT_FOUND);
    }
}
