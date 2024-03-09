package org.hayo.jobsy.userservice.models.exception;

import org.hayo.jobsy.utils.ValidationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Provided name should be minimum 3 chars long.")
public class InvalidNameMinLenghtException extends AbstractWebExceptions {
    public InvalidNameMinLenghtException(String name) {
        super(String.format("Provided name: {%s} should be minimum{%d} chars long.", name, ValidationUtils.MIN_CHARS), HttpStatus.BAD_REQUEST);
    }
}
