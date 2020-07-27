package com.questionpro.newsapp.services;

import com.questionpro.newsapp.model.User;
import com.questionpro.newsapp.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends BaseServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    private static final String TEST_USER_USERNAME = "test";

    @Test
    void testGetUserByUsername() {
        when(applicationParameters.getHackerNewsBaseURL()).thenReturn("http://test-host.com/");
        when(applicationParameters.getUserEndpoint()).thenReturn("test-user-path");

        when(restTemplate.getForObject(any(URI.class), eq(User.class))).thenReturn(getTestUser());

        User result = userService.getByUsername(TEST_USER_USERNAME);

        verify(restTemplate, times(1)).getForObject(any(URI.class), eq(User.class));

        assertEquals(TEST_USER_USERNAME, result.getUsername());
    }

    private User getTestUser(){
        User user = new User();
        user.setCreatedOn(Instant.now().getEpochSecond());
        user.setUsername(TEST_USER_USERNAME);

        return user;
    }
}
