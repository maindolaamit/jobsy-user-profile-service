package org.hayo.jobsy.userservice.utils.mapper;

import org.hayo.jobsy.dto.users.CreateUserRequest;
import org.hayo.jobsy.dto.users.UserProfile;
import org.hayo.jobsy.enums.commons.YesNo;
import org.hayo.jobsy.enums.user.UserStatus;
import org.hayo.jobsy.userservice.models.entity.UserProfileEntity;

public class UserProfileMapper {

    public static UserProfileEntity toEntity(UserProfile userProfile) {
        return UserProfileEntity.builder()
                .id(userProfile.getId())
                .name(userProfile.getName())
                .headline(userProfile.getHeadline())
                .email(userProfile.getEmail())
                .phone(userProfile.getPhone())
                .openToWork(YesNo.valueOf(userProfile.getOpenToWork()))
                .resumeUrl(userProfile.getResumeUrl())
                .githubUrl(userProfile.getGithubUrl())
                .googleUrl(userProfile.getGoogleUrl())
//                .address(userProfile.getAddress())
                .location(userProfile.getLocation())
                .currentPosition(userProfile.getCurrentPosition())
                .skills(userProfile.getSkills())
                .educations(userProfile.getEducations())
                .experiences(userProfile.getExperiences())
                .build();
    }

    public static UserProfile toDto(UserProfileEntity userProfileEntity) {
        return UserProfile.builder()
                .id(userProfileEntity.getId())
                .name(userProfileEntity.getName())
                .headline(userProfileEntity.getHeadline())
                .email(userProfileEntity.getEmail())
                .phone(userProfileEntity.getPhone())
                .openToWork(userProfileEntity.getOpenToWork().toString())
                .resumeUrl(userProfileEntity.getResumeUrl())
                .githubUrl(userProfileEntity.getGithubUrl())
                .googleUrl(userProfileEntity.getGoogleUrl())
                .location(userProfileEntity.getLocation())
                .currentPosition(userProfileEntity.getCurrentPosition())
                .skills(userProfileEntity.getSkills())
                .educations(userProfileEntity.getEducations())
                .experiences(userProfileEntity.getExperiences())
                .build();
    }

    public static UserProfileEntity toEntity(CreateUserRequest request) {
        return UserProfileEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .openToWork(YesNo.YES)
                .googleUrl(request.getGoogleUrl())
                .githubUrl(request.getGithubUrl())
                .build();
    }
}
