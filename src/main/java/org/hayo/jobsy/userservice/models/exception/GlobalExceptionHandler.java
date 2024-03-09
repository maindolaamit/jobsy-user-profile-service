package org.hayo.jobsy.userservice.models.exception;

import lombok.extern.slf4j.Slf4j;
import org.hayo.jobsy.dto.response.ApiErrorCauses;
import org.hayo.jobsy.dto.response.ApiErrorSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static ApiErrorSchema getApiErrorSchema(AbstractWebExceptions e) {
        String type = Object.class.getSimpleName();
        List<ApiErrorCauses> causes = List.of(
                ApiErrorCauses.builder().type(type)
                        .detail(e.getMessage()).build()
        );
        return ApiErrorSchema.builder()
                .type(e.getStatus().toString())
                .detail(e.getReason())
                .causes(causes).build();
    }

//    @ExceptionHandler(InvalidEmailException.class)
//    public ResponseEntity<ApiErrorSchema> handleUserNotRegistered(InvalidEmailException e) {
//        ApiErrorSchema schema = getApiErrorSchema(e);
//
//        log.error(e.getMessage());
//        return new ResponseEntity<>(schema, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InvalidVerificationCodeException.class)
//    public ResponseEntity<ApiErrorSchema> handleUserNotRegistered(InvalidVerificationCodeException e) {
//        ApiErrorSchema schema = getApiErrorSchema(e);
//
//        log.error(e.getMessage());
//        return new ResponseEntity<>(schema, HttpStatus.NOT_FOUND);
//    }
//
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

        ApiErrorSchema schema = new ApiErrorSchema(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "An error occurred",
                Collections.singletonList(causes));
        log.error("An error occurred {}", e.getMessage());

        log.error(e.getMessage());
        return new ResponseEntity<>(schema, HttpStatus.NOT_FOUND);
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
