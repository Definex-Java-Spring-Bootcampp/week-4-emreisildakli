package com.patika.kredinbizdeservice.listener;

import com.patika.kredinbizdeservice.exceptions.dto.ExceptionModel;
import com.patika.kredinbizdeservice.service.ExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExceptionListener {
    @Autowired
    private ExceptionService exceptionService;

    @KafkaListener(topics = "exception", groupId = "exceptions")
    public String listen(String message) {
        log.info("Consumer consume Kafka message -> {}", message);
        exceptionService.save(ExceptionModel.builder().message(message).build());
        return message;
    }
}