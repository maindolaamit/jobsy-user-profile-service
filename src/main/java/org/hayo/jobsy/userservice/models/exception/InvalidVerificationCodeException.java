package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;

public class InvalidVerificationCodeException extends AbstractWebExceptions {
    public InvalidVerificationCodeException() {
        super("Invalid verification code", HttpStatus.BAD_REQUEST);
    }
}
