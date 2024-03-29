package org.hayo.jobsy.userservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.jobsy.dto.response.BasicApiResponse;
import org.hayo.jobsy.dto.users.CreateUserRequest;
import org.hayo.jobsy.dto.users.Experience;
import org.hayo.jobsy.dto.users.UserProfile;
import org.hayo.jobsy.enums.user.UserStatus;
import org.hayo.jobsy.userservice.models.exception.UserEmailAlreadyExistsException;
import org.hayo.jobsy.userservice.models.exception.UserEmailNotExistsException;
import org.hayo.jobsy.userservice.models.exception.UserNotExistsException;
import org.hayo.jobsy.userservice.repository.UserProfileRepository;
import org.hayo.jobsy.userservice.service.UserProfileService;
import org.hayo.jobsy.userservice.utils.mapper.UserProfileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile createUserProfile(CreateUserRequest request) {
        log.info("Creating user profile for email {}", request.getEmail());
        // check if email alredy exists
        val byEmail = userProfileRepository.findByEmail(request.getEmail());
        if (byEmail != null) throw new UserEmailAlreadyExistsException(request.getEmail());
        val entity = UserProfileMapper.toEntity(request);
        val saved = userProfileRepository.save(entity);
        return UserProfileMapper.toDto(saved);
    }

    @Override
    public UserProfile getUserProfileByEmail(String email) {
        val byEmail = userProfileRepository.findByEmail(email);
        if (byEmail != null) return UserProfileMapper.toDto(byEmail);
        throw new UserEmailNotExistsException(email);
    }

    @Override
    public BasicApiResponse deleteUserProfile(String id) {
        log.info("Deleting user profile for id {}", id);
        val byEmail = userProfileRepository.findById(id);
        if (byEmail.isEmpty())
            throw new UserNotExistsException(id);
        userProfileRepository.deleteById(id);
        return BasicApiResponse.builder().message("User profile deleted successfully").build();
    }

    @Override
    public void deactivateUserProfile(String id) {
        val entity = userProfileRepository.findById(id)
                .orElseThrow(() -> new UserNotExistsException(id));

        entity.setUserStatus(UserStatus.SUSPENDED);
        userProfileRepository.save(entity);
    }

    @Override
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll().stream()
                .limit(10)
                .map(UserProfileMapper::toDto)
                .toList();
    }

    @Override
    public UserProfile getUserProfileById(String id) {
        val entity = userProfileRepository.findById(id).orElseThrow(() -> new UserNotExistsException(id));
        return UserProfileMapper.toDto(entity);
    }

    @Override
    public UserProfile addUserExperience(String userId, Experience request) {
        var entity = userProfileRepository.findById(userId).orElseThrow(() -> new UserNotExistsException(userId));

        entity.getExperiences().add(request);
        entity = userProfileRepository.save(entity);
        return UserProfileMapper.toDto(entity);
    }

    @Override
    public UserProfile addUserSkills(String userId, List<String> skills) {
        if(skills == null || skills.isEmpty()) throw new IllegalArgumentException("Skills cannot be null");

        var entity = userProfileRepository.findById(userId).orElseThrow(() -> new UserNotExistsException(userId));
        // add new skills to existing skills
        if (entity.getSkills() != null) skills.addAll(entity.getSkills());
        skills = skills.stream().filter(s -> !s.isBlank()).toList();

        entity.setSkills(skills);
        entity = userProfileRepository.save(entity);
        return UserProfileMapper.toDto(entity);
    }

    @Override
    public UserProfile updateUserSkills(String userId, List<String> request) {
        var entity = userProfileRepository.findById(userId).orElseThrow(() -> new UserNotExistsException(userId));
        entity.setSkills(request);
        entity = userProfileRepository.save(entity);
        return UserProfileMapper.toDto(entity);
    }
}
