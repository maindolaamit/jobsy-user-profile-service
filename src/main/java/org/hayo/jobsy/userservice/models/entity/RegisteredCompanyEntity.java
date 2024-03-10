package org.hayo.jobsy.userservice.models.entity;

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
@Document(collection = "company_registrations")
public class RegisteredCompanyEntity extends AbstractEntity {
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String verificationCode;
    private LocalDateTime verificationExpiry;
    private String companyWebsiteUrl;
}
