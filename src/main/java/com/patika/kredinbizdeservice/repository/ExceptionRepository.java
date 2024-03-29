package com.patika.kredinbizdeservice.repository;

import com.patika.kredinbizdeservice.exceptions.dto.ExceptionModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExceptionRepository extends MongoRepository<ExceptionModel, String> {

    ExceptionModel save(ExceptionModel entity);
}
