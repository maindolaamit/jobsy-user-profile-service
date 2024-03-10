package org.hayo.jobsy.userservice.utils.mapper;


import org.hayo.jobsy.dto.registration.RegisterUserRequest;
import org.hayo.jobsy.dto.registration.RegisteredUser;
import org.hayo.jobsy.dto.registration.RegisteredUserResponse;
import org.hayo.jobsy.dto.registration.VerifiedUserResponse;
import org.hayo.jobsy.dto.users.CreateUserRequest;
import org.hayo.jobsy.dto.users.UserCreatedResponse;
import org.hayo.jobsy.userservice.models.entity.RegisteredUserEntity;
import org.hayo.jobsy.userservice.utils.RegistrationUtility;

import java.time.LocalDateTime;

public class UserRegistrationMapper {
    public static RegisteredUserResponse toResponse(RegisteredUserEntity entity) {
        String link = RegistrationUtility.getVerificationUrl(entity.getEmail(), entity.getVerificationCode());
        return RegisteredUserResponse.builder()
                .message("User registered successfully with email " + entity.getEmail())
                .verificationUrl(link)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static VerifiedUserResponse toVerifiedUserResponse(UserCreatedResponse response) {
        return VerifiedUserResponse.builder()
                .message("User verified successfully.")
                .userProfileUrl(response.getUserProfileUrl())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static RegisteredUserEntity toEntity(RegisterUserRequest dto) {
        return RegisteredUserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .createdBy("system")
                .password(dto.getPassword())
                .verificationCode(RegistrationUtility.getVerificationCode())
                .verificationExpiry(LocalDateTime.now().plusDays(2))
                .build();
    }

    public static CreateUserRequest createUserRequestFromEntity(RegisteredUserEntity entity) {
        return CreateUserRequest.builder()
                .email(entity.getEmail())
                .name(entity.getName())
                .githubUrl(entity.getGithubUrl())
                .googleUrl(entity.getGoogleUrl())
                .build();
    }

    public static RegisteredUser toRegisteredUserDto(RegisteredUserEntity registeredUserEntity) {
        return RegisteredUser.builder()
                .name(registeredUserEntity.getName())
                .email(registeredUserEntity.getEmail())
                .githubUrl(registeredUserEntity.getGithubUrl())
                .googleUrl(registeredUserEntity.getGoogleUrl())
                .build();
    }
}
