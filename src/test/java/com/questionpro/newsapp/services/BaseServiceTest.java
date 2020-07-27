package com.questionpro.newsapp.services;

import com.questionpro.newsapp.config.ApplicationParameters;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

public abstract class BaseServiceTest {

    @Mock
    protected RestTemplate restTemplate;

    @Mock
    protected ApplicationParameters applicationParameters;
}
