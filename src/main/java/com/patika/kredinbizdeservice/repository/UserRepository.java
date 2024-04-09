package com.patika.kredinbizdeservice.repository;

import com.patika.kredinbizdeservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {


    Optional<User> findByEmail(String email);

}
