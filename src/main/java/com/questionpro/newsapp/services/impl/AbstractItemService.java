package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.config.ApplicationParameters;
import com.questionpro.newsapp.model.Item;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.questionpro.newsapp.common.Constants.ID_KEY;

public abstract class AbstractItemService<T extends Item> {
    protected final ApplicationParameters applicationParameters;
    protected final RestTemplate restTemplate;

    private final Map<String, String> pathParams = new HashMap<>();
    private final UriComponentsBuilder uriComponentsBuilder;

    protected AbstractItemService(ApplicationParameters applicationParameters, RestTemplate restTemplate) {
        this.applicationParameters = applicationParameters;
        this.restTemplate = restTemplate;
        this.uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(applicationParameters.getHackerNewsBaseURL())
                .path(applicationParameters.getItemEndpoint());
    }

    protected T get(String id, final Class<T> clazz) {

        pathParams.put(ID_KEY, id);

        URI url = uriComponentsBuilder
                .buildAndExpand(pathParams)
                .toUri();

        T response = restTemplate.getForObject(url, clazz);

        return response;
    }
}
