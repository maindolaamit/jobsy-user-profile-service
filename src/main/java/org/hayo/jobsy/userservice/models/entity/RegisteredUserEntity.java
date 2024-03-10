package org.hayo.jobsy.userservice.models.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "user_registrations")
public class RegisteredUserEntity extends AbstractEntity {
    @NotNull
    @NotBlank
    private String name;
    @Indexed(unique = true)
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;
    private String verificationCode;
    private LocalDateTime verificationExpiry;
    private String githubUrl;
    private String googleUrl;
}
