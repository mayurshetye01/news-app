package com.questionpro.newsapp.services;

import com.questionpro.newsapp.config.ApplicationParameters;
import com.questionpro.newsapp.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import static com.questionpro.newsapp.common.Constants.ID_KEY;

@Slf4j
public abstract class AbstractItemService<T extends Item> {
    protected final ApplicationParameters applicationParameters;
    protected final RestTemplate restTemplate;

    protected AbstractItemService(ApplicationParameters applicationParameters, RestTemplate restTemplate) {
        this.applicationParameters = applicationParameters;
        this.restTemplate = restTemplate;
    }

    protected T get(String id, final Class<T> clazz) {
        final Map<String, String> pathParams = new HashMap<>();
        pathParams.put(ID_KEY, id);

        URI url = UriComponentsBuilder
                .fromHttpUrl(applicationParameters.getHackerNewsBaseURL())
                .path(applicationParameters.getItemEndpoint())
                .buildAndExpand(pathParams)
                .toUri();

        log.debug("Sending GET request for item, URL - {}", url);
        T response = restTemplate.getForObject(url, clazz);
        return response;
    }

    protected List<T> getItems(String[] itemIds, final Class<T> clazz) {
        List<T> items = new ArrayList<>();

        List<CompletableFuture<T>> completableFutures = new ArrayList<>();

        //ExecutorService executorService = Executors.newFixedThreadPool(applicationParameters.getThreadPoolSize());
        ForkJoinPool forkJoinPool = new ForkJoinPool(applicationParameters.getThreadPoolSize());

        for (String itemId : itemIds) {
            CompletableFuture<T> completableFuture = CompletableFuture.supplyAsync(() -> get(itemId, clazz), forkJoinPool);
            completableFutures.add(completableFuture);
        }
        forkJoinPool.shutdown();

        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

        completableFutures.forEach(completableFuture -> {
            try {
                items.add(completableFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception while adding future", e);
            }
        });

        return items;
    }
}
