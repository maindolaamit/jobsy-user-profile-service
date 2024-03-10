package org.hayo.jobsy.userservice.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hayo.jobsy.dto.Address;
import org.hayo.jobsy.dto.users.Education;
import org.hayo.jobsy.dto.users.Experience;
import org.hayo.jobsy.dto.users.Salary;
import org.hayo.jobsy.enums.commons.YesNo;
import org.hayo.jobsy.enums.user.UserStatus;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "user_profiles")
public class UserProfileEntity extends AbstractEntity {
    private String name;
    private String headline;
    @Indexed(unique = true)
    private String email;
    private String phone;
    private YesNo openToWork;
    private UserStatus userStatus;
    private String userProfileImageUrl;
    private String resumeUrl;
    private String googleUrl;
    private String githubUrl;
    private Salary salary;
    private LocalDateTime lastLogin;
    private Address address;
    private String currentPosition;
    private List<String> skills;
    private List<Education> educations;
    private List<Experience> experiences;
}
