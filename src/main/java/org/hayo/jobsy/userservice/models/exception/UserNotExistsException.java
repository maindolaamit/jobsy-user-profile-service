package org.hayo.jobsy.userservice.models.exception;

import org.hayo.jobsy.models.exceptions.AbstractWebExceptions;
import org.springframework.http.HttpStatus;

public class UserNotExistsException extends AbstractWebExceptions {

    public UserNotExistsException(String user) {
        super("User does not exists: " + user, HttpStatus.NOT_FOUND);
    }
}
