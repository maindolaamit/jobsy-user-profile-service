package org.hayo.jobsy.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.jobsy.dto.registration.RegisterUserRequest;
import org.hayo.jobsy.dto.registration.RegisteredUser;
import org.hayo.jobsy.dto.registration.RegisteredUserResponse;
import org.hayo.jobsy.dto.registration.VerifyRegisteredUserRequest;
import org.hayo.jobsy.dto.users.UserProfile;
import org.hayo.jobsy.userservice.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/register")
@Tag(name = "Register", description = "Registration Module")
public class CompnyRegistrationController {

    private final RegistrationService registrationService;

    public CompnyRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/verify-user")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Verify user registration", description = "API to verify user registration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully verified",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserProfile> verifyUser(@Valid @RequestBody VerifyRegisteredUserRequest request) {
        UserProfile schema = registrationService.verifyUser(request);
        return new ResponseEntity<>(schema, HttpStatus.CREATED);
    }


    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Application", description = "API to create an application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<RegisteredUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest user) {
        log.info("Registering user {}", user.getEmail());
        val response = registrationService.registerUser(user);
        log.info("User registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<RegisteredUser>> getAllRegisteredUsers() {
        log.info("Getting all registered users");
        val response = registrationService.getAllUsers(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
