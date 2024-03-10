package org.hayo.jobsy.userservice.service;


import org.hayo.jobsy.dto.registration.RegisterUserRequest;
import org.hayo.jobsy.dto.registration.RegisteredUser;
import org.hayo.jobsy.dto.registration.RegisteredUserResponse;
import org.hayo.jobsy.dto.registration.VerifyRegisteredUserRequest;
import org.hayo.jobsy.dto.users.UserProfile;

import java.time.LocalDate;
import java.util.List;

public interface RegistrationService {

    RegisteredUserResponse registerUser(RegisterUserRequest dto);

    UserProfile verifyUser(VerifyRegisteredUserRequest request);

    List<RegisteredUser> getAllUsers(LocalDate since);

}
