package org.hayo.jobsy.userservice.service;

import org.hayo.jobsy.dto.response.BasicApiResponse;
import org.hayo.jobsy.dto.users.CreateUserRequest;
import org.hayo.jobsy.dto.users.Experience;
import org.hayo.jobsy.dto.users.UserCreatedResponse;
import org.hayo.jobsy.dto.users.UserProfile;
import org.hayo.jobsy.userservice.models.entity.UserProfileEntity;

import java.util.List;

public interface UserProfileService {
    UserProfile createUserProfile(CreateUserRequest request);

    UserProfile getUserProfileByEmail(String email);

//    UserProfile updateUserProfile(UserProfileEntity userProfileEntity);

    BasicApiResponse deleteUserProfile(String email);

    void deactivateUserProfile(String email);

    List<UserProfile> getAllUserProfiles();

    UserProfile getUserProfileById(String id);

    UserProfile addUserExperience(String userId, Experience request);

    UserProfile addUserSkills(String userId, List<String> request);

    UserProfile updateUserSkills(String userId, List<String> request);
}
