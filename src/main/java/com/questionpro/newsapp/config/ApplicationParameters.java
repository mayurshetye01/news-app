package com.questionpro.newsapp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationParameters {
    @Value("${hacker-news-base-url}")
    private String hackerNewsBaseURL;


    @Value("${item-endpoint}")
    private String itemEndpoint;

    @Value("${best-stories-endpoint}")
    private String bestStoriesEndpoint;

    @Value("${user-endpoint}")
    private String userEndpoint;
}
