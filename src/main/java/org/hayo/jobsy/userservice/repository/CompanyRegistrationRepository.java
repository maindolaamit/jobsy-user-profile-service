package org.hayo.jobsy.userservice.repository;

import org.hayo.jobsy.userservice.models.entity.RegisteredCompanyEntity;
import org.hayo.jobsy.userservice.models.entity.RegisteredUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRegistrationRepository extends MongoRepository<RegisteredCompanyEntity, String> {
    RegisteredUserEntity findByEmail(String email);
}
