package org.hayo.jobsy.userservice.models.exception;

import org.springframework.http.HttpStatus;

public class ExpiredVerificationCodeException extends AbstractWebExceptions {
    public ExpiredVerificationCodeException() {

        super("Verification code has expired, please request a new one.",
                HttpStatus.BAD_REQUEST);
    }
}
