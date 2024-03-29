package com.patika.kredinbizdeservice.exceptions.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document("exception")
public class ExceptionModel {

    private String message;


}
