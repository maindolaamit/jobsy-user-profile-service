package org.hayo.jobsy.userservice.models.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hayo.jobsy.dto.response.ApiErrorSchema;
import org.springframework.http.HttpStatusCode;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Getter
@Builder
@AllArgsConstructor
public class ApiConnectionError extends RuntimeException {

    private final HttpStatusCode httpStatusCode;

    private final String message;

    private final Optional<ApiErrorSchema> apiErrorSchema;
}
