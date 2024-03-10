package org.hayo.jobsy.userservice.models.exception;

import org.hayo.jobsy.models.exceptions.AbstractWebExceptions;
import org.springframework.http.HttpStatus;

public class UserEmailAlreadyExistsException extends AbstractWebExceptions {
    public UserEmailAlreadyExistsException(String email) {

        super("User email already registered : " + email, HttpStatus.NOT_FOUND);
    }
}
