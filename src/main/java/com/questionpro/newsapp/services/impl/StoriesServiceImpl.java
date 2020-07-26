package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.config.ApplicationParameters;
import com.questionpro.newsapp.entity.PastStory;
import com.questionpro.newsapp.model.Story;
import com.questionpro.newsapp.repository.PastStoriesRepository;
import com.questionpro.newsapp.services.StoriesService;
import com.questionpro.newsapp.util.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StoriesServiceImpl extends AbstractItemService<Story> implements StoriesService {
    private final PastStoriesRepository pastStoriesRepository;

    public StoriesServiceImpl(ApplicationParameters applicationParameters, RestTemplate restTemplate,
                              PastStoriesRepository pastStoriesRepository) {
        super(applicationParameters, restTemplate);
        this.pastStoriesRepository = pastStoriesRepository;
    }

    @Override
    public Story get(String storyId) {
        return get(storyId, Story.class);
    }

    @Override
    public List<Story> getBestStories() {
        URI url = UriComponentsBuilder
                .fromHttpUrl(applicationParameters.getHackerNewsBaseURL())
                .path(applicationParameters.getBestStoriesEndpoint())
                .build()
                .toUri();
        String[] storyIds = this.restTemplate.getForObject(url, String[].class);
        List<Story> stories = getItems(storyIds, Story.class);

        return stories;
    }

    @Override
    public List<Story> getPastStories() {
        List<Story> result = new ArrayList<>();
        List<PastStory> pastStories = pastStoriesRepository.findAll();
        if (pastStories == null)
            return result;

        pastStories.forEach(pastStory -> result.add(ConverterUtil.convert(pastStory)));

        return result;
    }

}
