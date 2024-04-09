package com.patika.kredinbizdeservice.model;


import com.patika.kredinbizdeservice.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("application")
public class Application {

    @Id
    private Long applicationId;
    private Loan loan;
    private Product product;
    private Long userId;
    private LocalDateTime createDate;
    private ApplicationStatus applicationStatus;

    public Application() {
    }


}
