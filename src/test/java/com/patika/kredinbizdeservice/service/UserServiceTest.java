package com.patika.kredinbizdeservice.service;

import com.patika.kredinbizdeservice.exceptions.KredinbizdeException;
import com.patika.kredinbizdeservice.model.User;
import com.patika.kredinbizdeservice.producer.NotificationProducer;
import com.patika.kredinbizdeservice.producer.dto.NotificationDTO;
import com.patika.kredinbizdeservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationProducer notificationProducer;


    @Test
    public void should_create_user_successfully() {
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(prepareUser());

        User userResponse = userService.save(prepareUser());

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getName()).isEqualTo(prepareUser().getName());
        assertThat(userResponse.getSurname()).isEqualTo(prepareUser().getSurname());
        assertThat(userResponse.getEmail()).isEqualTo(prepareUser().getEmail());
        assertThat(userResponse.getPassword()).isEqualTo(prepareUser().getPassword());

        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verify(notificationProducer, times(1)).sendNotification(Mockito.any(NotificationDTO.class));

    }

    @Test
    public void should_return_user_by_email_successfully() {
        Mockito.when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(prepareUser()));

        User userResponse = userService.getByEmail("test@gmail.com");

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getName()).isEqualTo(prepareUser().getName());
        assertThat(userResponse.getSurname()).isEqualTo(prepareUser().getSurname());
        assertThat(userResponse.getEmail()).isEqualTo(prepareUser().getEmail());
        assertThat(userResponse.getPassword()).isEqualTo(prepareUser().getPassword());

        verify(userRepository, times(1)).findByEmail("test@gmail.com");
    }

    @Test
    public void should_return_user_by_userId_successfully() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(prepareUser()));

        User userResponse = userService.getById(1L);

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getName()).isEqualTo(prepareUser().getName());
        assertThat(userResponse.getSurname()).isEqualTo(prepareUser().getSurname());
        assertThat(userResponse.getEmail()).isEqualTo(prepareUser().getEmail());
        assertThat(userResponse.getPassword()).isEqualTo(prepareUser().getPassword());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void should_throw_kredinbizdeException_whenUserNotFound() {
        Throwable throwable = catchThrowable(() -> userService.getByEmail("test@gmail.com"));

        assertThat(throwable).isInstanceOf(KredinbizdeException.class);
    }

    @Test
    public void should_update_user_successfully() {
        Mockito.when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(prepareUser()));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(prepareUser());
        doNothing().when(userRepository).delete(Mockito.any(User.class));

        User userResponse = userService.update("test@gmail.com", prepareUser());

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getName()).isEqualTo(prepareUser().getName());
        assertThat(userResponse.getSurname()).isEqualTo(prepareUser().getSurname());
        assertThat(userResponse.getEmail()).isEqualTo(prepareUser().getEmail());
        assertThat(userResponse.getPassword()).isEqualTo(prepareUser().getPassword());

        verify(userRepository, times(1)).findByEmail("test@gmail.com");
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verify(userRepository, times(1)).delete(Mockito.any(User.class));
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
