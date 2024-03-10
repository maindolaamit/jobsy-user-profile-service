package org.hayo.jobsy.userservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.jobsy.dto.registration.RegisterUserRequest;
import org.hayo.jobsy.dto.registration.RegisteredUser;
import org.hayo.jobsy.dto.registration.RegisteredUserResponse;
import org.hayo.jobsy.dto.registration.VerifyRegisteredUserRequest;
import org.hayo.jobsy.dto.users.UserProfile;
import org.hayo.jobsy.userservice.models.entity.RegisteredUserEntity;
import org.hayo.jobsy.userservice.models.exception.EmailAlreadyRegisteredException;
import org.hayo.jobsy.userservice.models.exception.ExpiredVerificationCodeException;
import org.hayo.jobsy.userservice.models.exception.InvalidVerificationCodeException;
import org.hayo.jobsy.userservice.models.exception.UserNotRegisteredException;
import org.hayo.jobsy.userservice.repository.UserRegistrationRepository;
import org.hayo.jobsy.userservice.service.RegistrationService;
import org.hayo.jobsy.userservice.service.UserProfileService;
import org.hayo.jobsy.userservice.utils.mapper.UserRegistrationMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRegistrationRepository repository;
    private final UserProfileService userProfileService;

    @Override
    public RegisteredUserResponse registerUser(RegisterUserRequest dto) {
        val byEmail = repository.findByEmail(dto.getEmail());
        if (byEmail != null) {
            log.error("User {} already registered id: {}", dto.getEmail(),
                    byEmail.getId());
            throw new EmailAlreadyRegisteredException(dto.getEmail());
        }

        try {
            log.debug("Registering user {}", dto.getEmail());
            val newEntity = repository.save(UserRegistrationMapper.toEntity(dto));
            log.debug("User {} registered successfully", newEntity.getId());
            return UserRegistrationMapper.toResponse(newEntity);
        } catch (
                Exception e) {
            log.error("Error occurred while registering user", e);
            throw new RuntimeException("Error occurred while registering user", e);
        }
    }

    @Override
    public UserProfile verifyUser(VerifyRegisteredUserRequest request) {
        val entity = getVerifyTokenUserEntity(request.getEmail(), request.getToken());
        log.debug("User {} verified", entity.getEmail());
        val req = UserRegistrationMapper.createUserRequestFromEntity(entity);
        return userProfileService.createUserProfile(req);

//        val entity = UserProfileMapper.toEntity(request);
//        val saved = userProfileRepository.save(entity);
//        return UserProfileMapper.toDto(saved);
    }


    public RegisteredUserEntity getVerifyTokenUserEntity(String email, String verificationCode) {
        val byEmail = repository.findByEmail(email);
        // check if not null
        if (byEmail == null) {
            throw new UserNotRegisteredException(email);
        } else {
            // check if the verification code is valid and not expired
            if (!byEmail.getVerificationCode().equals(verificationCode)) {
                log.error("Invalid verification code for user {}", email);
                throw new InvalidVerificationCodeException();
            }
            if (!byEmail.getVerificationExpiry().isAfter(LocalDateTime.now())) {
                log.error("Verification code expired for user {}", email);
                throw new ExpiredVerificationCodeException();
            }
            return byEmail;
        }
    }

    @Override
    public List<RegisteredUser> getAllUsers(LocalDate since) {
        // set for last 3 days
        if (since == null) since = LocalDate.now().minusDays(3);
//        val localDateTime = LocalDateTime.of(since, LocalDateTime.MIN.toLocalTime());
        return repository.findAll().stream().map(
                        UserRegistrationMapper::toRegisteredUserDto)
                .collect(Collectors.toList());
    }
}

