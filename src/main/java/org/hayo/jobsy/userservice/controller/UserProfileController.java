package org.hayo.jobsy.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hayo.jobsy.dto.response.BasicApiResponse;
import org.hayo.jobsy.dto.users.CreateUserRequest;
import org.hayo.jobsy.dto.users.UserProfile;
import org.hayo.jobsy.userservice.models.exception.InvalidEmailException;
import org.hayo.jobsy.userservice.models.exception.InvalidNameMinLenghtException;
import org.hayo.jobsy.userservice.service.UserProfileService;
import org.hayo.jobsy.utils.ValidationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/user-profiles")
@Tag(name = "User Profiles", description = "User Profiles Module")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete user Profile", description = "API to get delete user profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<BasicApiResponse> deleteUser(@PathVariable String id) {
        if(!ValidationUtils.hasMinChars(id)) throw new IllegalArgumentException("Invalid name");
        log.info("Searching user with id {}", id);
        val basicApiResponse = userProfileService.deleteUserProfile(id);
        return new ResponseEntity<>(basicApiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user Profile by ID or Email", description = "API to get user profile by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json")),
    })

    public ResponseEntity<UserProfile> searchUserByEmail(@RequestParam(required = false) String email,
                                                         @RequestParam(required = false) String id) {
        UserProfile userProfile;
        if (email != null) {
            if (!ValidationUtils.isValidEmail(email)) throw new InvalidEmailException(email);
            log.info("Searching user with email {}", email);
            userProfile = userProfileService.getUserProfileByEmail(email);
        } else if (id != null) {
            if (!ValidationUtils.hasMinChars(id)) throw new IllegalArgumentException("Invalid id");
            log.info("Searching user with id {}", id);
            userProfile = userProfileService.getUserProfileById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Either email or id must be provided");
        }
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user Profile", description = "API to get user profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<UserProfile> searchUser(@PathVariable String id) {
        if(!ValidationUtils.hasMinChars(id)) throw new IllegalArgumentException("Invalid name");
        log.info("Searching user with id {}", id);
        UserProfile schema = userProfileService.getUserProfileById(id);
        return new ResponseEntity<>(schema, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add user Profile", description = "API to add user profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<UserProfile> createUser(@RequestBody CreateUserRequest request) {
        if(!ValidationUtils.isValidEmail(request.getEmail())) throw new InvalidEmailException(request.getEmail());
        if(!ValidationUtils.hasMinChars(request.getName())) throw new InvalidNameMinLenghtException(request.getName());

        log.info("Creating user with email {}", request.getEmail());
        UserProfile schema = userProfileService.createUserProfile(request);
        return new ResponseEntity<>(schema, HttpStatus.CREATED);
    }


    @PostMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Application", description = "API to create an application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<BasicApiResponse> deactivateUser(@PathVariable String id) {
        if(!ValidationUtils.hasMinChars(id)) throw new IllegalArgumentException("Invalid name");
        log.info("Deactivating user with id {}", id);
        userProfileService.deactivateUserProfile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserProfile>> getAllRegisteredUsers() {
        log.info("Getting all registered users");
        val response = userProfileService.getAllUserProfiles();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
