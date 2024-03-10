package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;

public class UserNotRegisteredException extends AbstractWebExceptions {

    public UserNotRegisteredException(String user) {
        super("User not registered: " + user, HttpStatus.NOT_FOUND);
    }
}
