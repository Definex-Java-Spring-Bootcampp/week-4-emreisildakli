package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.client.AkbankServiceClient;
import com.patika.kredinbizdeservice.client.GarantiServiceClient;
import com.patika.kredinbizdeservice.client.dto.request.GarantiApplicationRequest;
import com.patika.kredinbizdeservice.client.dto.response.ApplicationResponse;
import com.patika.kredinbizdeservice.converter.ApplicationConverter;
import com.patika.kredinbizdeservice.dto.request.ApplicationRequest;
import com.patika.kredinbizdeservice.model.Application;
import com.patika.kredinbizdeservice.model.User;
import com.patika.kredinbizdeservice.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationConverter applicationConverter;
    private final UserService userService;
    private final AkbankServiceClient akbankServiceClient;
    private final GarantiServiceClient garantiServiceClient;

    public Application createApplication(ApplicationRequest request) {

        User user = userService.getByEmail(request.getEmail());
        log.info("user bulundu");

        Application application = applicationConverter.toApplication(request, user);
        application.setApplicationId(new Random().nextLong());
        Application savedApplication = applicationRepository.save(application);

        ApplicationResponse garantiApplicationResponse = garantiServiceClient.createApplication(prepareGarantiApplicationRequest(user));
        return savedApplication;
    }

    public List<ApplicationResponse> getApplicationsByEmail(String email) {
        User user = userService.getByEmail(email);

        List<ApplicationResponse> garantiApplicationsResponses = garantiServiceClient.getApplicationsByUserId(user.getUserId());

        return garantiApplicationsResponses;

    }


    private GarantiApplicationRequest prepareGarantiApplicationRequest(User user) {
        GarantiApplicationRequest applicationRequest = new GarantiApplicationRequest();

        applicationRequest.setUserId(user.getUserId());

        return applicationRequest;
    }


}
