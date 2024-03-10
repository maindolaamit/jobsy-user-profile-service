package org.hayo.jobsy.userservice.repository;

import org.hayo.jobsy.userservice.models.entity.RegisteredUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends MongoRepository<RegisteredUserEntity, String>{
    RegisteredUserEntity findByEmail(String email);
}
