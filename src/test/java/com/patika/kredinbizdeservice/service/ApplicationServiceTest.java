package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.client.GarantiServiceClient;
import com.patika.kredinbizdeservice.client.dto.request.GarantiApplicationRequest;
import com.patika.kredinbizdeservice.client.dto.response.ApplicationResponse;
import com.patika.kredinbizdeservice.converter.ApplicationConverter;
import com.patika.kredinbizdeservice.dto.request.ApplicationRequest;
import com.patika.kredinbizdeservice.enums.ApplicationStatus;
import com.patika.kredinbizdeservice.model.Application;
import com.patika.kredinbizdeservice.model.User;
import com.patika.kredinbizdeservice.producer.NotificationProducer;
import com.patika.kredinbizdeservice.repository.ApplicationRepository;
import com.patika.kredinbizdeservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
    @InjectMocks
    private ApplicationService applicationService;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private UserService userService;
    @Mock
    private ApplicationConverter applicationConverter;
    @Mock
    private NotificationProducer notificationProducer;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GarantiServiceClient garantiServiceClient;


    @Test
    public void should_create_application_successfully() {
        Mockito.when(userService.getByEmail(prepareApplicationRequest().getEmail())).thenReturn(prepareUser());
        Mockito.when(applicationConverter.toApplication(Mockito.any(ApplicationRequest.class), Mockito.any(User.class))).thenReturn(prepareApplication());
        Mockito.when(garantiServiceClient.createApplication(Mockito.any(GarantiApplicationRequest.class))).thenReturn(prepareApplicationResponse());
        Mockito.when(applicationRepository.save(Mockito.any(Application.class))).thenReturn(prepareApplication());


        Application applicationResponse = applicationService.createApplication(prepareApplicationRequest());

        assertThat(applicationResponse).isNotNull();
        assertThat(applicationResponse.getCreateDate()).isNotNull();
        assertThat(applicationResponse.getApplicationStatus()).isEqualTo(prepareApplication().getApplicationStatus());


        verify(applicationRepository, times(1)).save(Mockito.any(Application.class));
        verify(garantiServiceClient, times(1)).createApplication(Mockito.any(GarantiApplicationRequest.class));

    }

    @Test
    public void should_get_applications_byEmail_successfully() {
        Mockito.when(userService.getByEmail(prepareUser().getEmail())).thenReturn(prepareUser());
        Mockito.when(garantiServiceClient.getApplicationsByUserId(prepareUser().getUserId())).thenReturn(prepareApplicationResponseList());
        ;


        List<ApplicationResponse> applicationResponses = applicationService.getApplicationsByEmail(prepareUser().getEmail());

        assertThat(applicationResponses).isNotNull();
        assertThat(applicationResponses).isNotEmpty();

        verify(userService, times(1)).getByEmail(prepareUser().getEmail());
        verify(garantiServiceClient, times(1)).getApplicationsByUserId(prepareUser().getUserId());

    }


    private Application prepareApplication() {
        Application application = new Application();

        application.setApplicationStatus(ApplicationStatus.INITIAL);
        application.setApplicationId(1L);
        application.setUserId(1L);
        application.setCreateDate(LocalDateTime.of(2024, 04, 9, 14, 33));


        return application;
    }

    private List<ApplicationResponse> prepareApplicationResponseList() {
        List<ApplicationResponse> applicationList = new ArrayList<>();

        ApplicationResponse response = new ApplicationResponse();


        response.setApplicationStatus(com.patika.kredinbizdeservice.client.dto.response.ApplicationStatus.INITIAL);
        response.setUserId(1L);
        response.setCreateDate(LocalDateTime.of(2024, 04, 9, 14, 33));

        applicationList.add(response);
        return applicationList;
    }

    private ApplicationRequest prepareApplicationRequest() {
        ApplicationRequest request = new ApplicationRequest();

        request.setEmail("test@gmail.com");

        return request;
    }

    private ApplicationResponse prepareApplicationResponse() {


        return ApplicationResponse
                .builder()
                .applicationStatus(com.patika.kredinbizdeservice.client.dto.response.ApplicationStatus.INITIAL)
                .createDate(LocalDateTime.of(2024, 04, 9, 14, 33))
                .build();


    }

    private User prepareUser() {
        User user = new User();

        user.setName("test");
        user.setSurname("test surname");
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setUserId(1L);

        return user;
    }

}
