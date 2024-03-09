package org.hayo.jobsy.userservice.repository;

import org.hayo.jobsy.userservice.models.entity.UserProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfileEntity, String> {
    UserProfileEntity findByEmail(String email);
}
