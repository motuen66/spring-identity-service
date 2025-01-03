package com.motuen.identity_service.service;

import com.motuen.identity_service.dto.request.UserCreationRequest;
import com.motuen.identity_service.dto.response.UserResponse;
import com.motuen.identity_service.entity.User;
import com.motuen.identity_service.exception.AppException;
import com.motuen.identity_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse response;
    private LocalDate dob;
    private User user;

    @BeforeEach
    public void initData(){
        dob = LocalDate.of(1990, 1, 1);

        request = UserCreationRequest.builder()
                .username("john01")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .dob(dob)
                .build();
        response = UserResponse.builder()
                .id("eyJhbGciOiJI")
                .username("john01")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

        user = User.builder()
                .id("eyJhbGciOiJI")
                .username("john01")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success(){
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        //WHEN
        var creationResponse = userService.createUser(request);

        //THEN
        assertThat(creationResponse.getId()).isEqualTo("eyJhbGciOiJ");
        assertThat(creationResponse.getUsername()).isEqualTo("john02");
    }

    @Test
    void createUser_userExisted_fail(){
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        //WHEN
        var exception = assertThrows(AppException.class,
                () -> userService.createUser(request));

        //THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }
}
