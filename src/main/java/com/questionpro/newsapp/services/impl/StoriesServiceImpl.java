package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.config.ApplicationParameters;
import com.questionpro.newsapp.model.Story;
import com.questionpro.newsapp.services.StoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class StoriesServiceImpl extends AbstractItemService<Story> implements StoriesService {

    public StoriesServiceImpl(ApplicationParameters applicationParameters, RestTemplate restTemplate) {
        super(applicationParameters, restTemplate);
    }

    @Override
    public List<Story> getBestStories() {
        URI url = UriComponentsBuilder
                .fromHttpUrl(applicationParameters.getHackerNewsBaseURL())
                .path(applicationParameters.getBestStoriesEndpoint())
                .build()
                .toUri();
        String[] storyIds = this.restTemplate.getForObject(url, String[].class);
        List<Story> stories = getStories(storyIds);

        //TODO
        //Add to cache
        return stories;
    }

    private List<Story> getStories(String[] storyIds) {
        List<Story> stories = new ArrayList<>();

        List<CompletableFuture<Story>> completableFutures = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (String storyId : storyIds) {
            CompletableFuture<Story> completableFuture = CompletableFuture.supplyAsync(() -> get(storyId, Story.class), executorService);
            completableFutures.add(completableFuture);
        }

        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

        completableFutures.forEach(completableFuture -> {
            try {
                stories.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception while adding future", e);
            }
        });

        return stories;
    }

    @Override
    public List<Story> getPastStories() {
        //TODO
        return null;
    }
}
