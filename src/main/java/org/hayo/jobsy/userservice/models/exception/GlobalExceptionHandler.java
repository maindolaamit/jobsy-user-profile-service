package org.hayo.jobsy.userservice.models.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hayo.jobsy.dto.response.ApiErrorCauses;
import org.hayo.jobsy.dto.response.ApiErrorSchema;
import org.hayo.jobsy.models.exceptions.AbstractWebExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hayo.jobsy.models.exceptions.ExceptionUtility.getApiErrorSchema;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractWebExceptions.class)
    public ResponseEntity<ApiErrorSchema> handleUserNotRegistered(AbstractWebExceptions e) {
        ApiErrorSchema schema = getApiErrorSchema(e);

        log.error(e.getMessage());
        return new ResponseEntity<>(schema, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorSchema> handleUserNotRegistered(IllegalArgumentException e) {
        String message = e.getMessage();
        ApiErrorCauses causes = ApiErrorCauses.builder().name(e.getClass().getSimpleName())
                .detail(message)
                .location("BODY")
                .build();

        ApiErrorSchema schema = new ApiErrorSchema(HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                Collections.singletonList(causes));
        log.error("An error occurred {}", e.getMessage());

        log.error(e.getMessage());
        return new ResponseEntity<>(schema, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ApiErrorCauses> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> errors.add(
                ApiErrorCauses.builder().detail(err.getDefaultMessage())
                        .location(err.getObjectName())
                        .detail(err.getDefaultMessage())
                        .value(String.valueOf(err.getRejectedValue()))
                        .name(err.getField())
                        .build()
        ));
//        ex.getAllErrors().forEach(err -> errors.add(
//                ApiErrorCauses.builder().detail(err.getDefaultMessage())
//                        .location("body")
//                        .name(err.getObjectName()).build()
//        ));

        ApiErrorSchema schema = ApiErrorSchema.builder()
                .type(HttpStatus.BAD_REQUEST.toString())
                .detail("Validation error")
                .causes(errors).build();

        return new ResponseEntity<>(schema, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> notValid(HandlerMethodValidationException ex, HttpServletRequest request) {
        List<ApiErrorCauses> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(
                ApiErrorCauses.builder().detail(err.getDefaultMessage())
                        .location("body")
                        .name("").build()
        ));

        ApiErrorSchema schema = ApiErrorSchema.builder()
                .type(HttpStatus.BAD_REQUEST.toString())
                .detail("Validation error")
                .causes(errors).build();

        return new ResponseEntity<>(schema, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorSchema> handleException(Exception e) {
        String type = "INTERNAL_ERROR";
        String message = "An internal server error occurred, please try again later";
        ApiErrorCauses causes = ApiErrorCauses.builder().name(type)
                .detail(message)
                .location("BODY")
                .build();

        ApiErrorSchema schema = new ApiErrorSchema(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "An error occurred",
                Collections.singletonList(causes));
        log.error("An error occurred {}", e.getMessage());
        return new ResponseEntity<>(schema, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
