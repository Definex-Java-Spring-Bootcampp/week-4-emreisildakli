package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.exceptions.dto.ExceptionModel;
import com.patika.kredinbizdeservice.repository.ExceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionService {

    @Autowired
    private ExceptionRepository exceptionRepository;


    public void save(ExceptionModel exception) {
        exceptionRepository.save(exception);
    }


}
