package org.hayo.jobsy.userservice.service;

import org.hayo.jobsy.dto.registration.RegisterUserRequest;
import org.hayo.jobsy.userservice.models.exception.InvalidEmailException;
import org.hayo.jobsy.userservice.models.exception.InvalidUrlException;
import org.hayo.jobsy.utils.ValidationUtils;

public class ValidationService {
    public static boolean validateRegisterUserRequest(RegisterUserRequest dto) throws RuntimeException {
        if (!ValidationUtils.isValidEmail(dto.getEmail()) ||
                !ValidationUtils.hasMinChars(dto.getName())) {
            throw new InvalidEmailException(dto.getEmail());
        }

        // check if the url is not empty & valid
        if (dto.getGithubUrl() != null && !ValidationUtils.isValidUrl(dto.getGoogleUrl()))
            throw new InvalidUrlException(dto.getGithubUrl());

        if (dto.getGoogleUrl() != null && !ValidationUtils.isValidUrl(dto.getGoogleUrl()))
            throw new IllegalArgumentException(dto.getGoogleUrl());

        return true;
    }
}
