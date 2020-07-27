package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.config.ApplicationParameters;
import com.questionpro.newsapp.model.User;
import com.questionpro.newsapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.questionpro.newsapp.common.Constants.USERNAME_KEY;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;
    private final ApplicationParameters applicationParameters;

    public UserServiceImpl(ApplicationParameters applicationParameters, RestTemplate restTemplate) {
        this.applicationParameters = applicationParameters;
        this.restTemplate = restTemplate;
    }

    @Override
    public User getByUsername(String username) {
        log.info("Getting user with username {}", username);
        final Map<String, String> pathParams = new HashMap<>();
        pathParams.put(USERNAME_KEY, username);

        URI url = UriComponentsBuilder
                .fromHttpUrl(applicationParameters.getHackerNewsBaseURL())
                .path(applicationParameters.getUserEndpoint())
                .buildAndExpand(pathParams)
                .toUri();
        log.debug("URL - {}", url);
        User user = this.restTemplate.getForObject(url, User.class);
        return user;
    }
}
