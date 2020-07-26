package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.config.CacheConfig;
import com.questionpro.newsapp.entity.PastStory;
import com.questionpro.newsapp.model.Story;
import com.questionpro.newsapp.repository.PastStoriesRepository;
import com.questionpro.newsapp.services.AbstractTransformationService;
import com.questionpro.newsapp.services.StoriesService;
import com.questionpro.newsapp.util.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class StoriesTransformationService extends AbstractTransformationService {
    private final StoriesService storiesService;
    private final PastStoriesRepository pastStoriesRepository;

    public StoriesTransformationService(StoriesService storiesService, PastStoriesRepository pastStoriesRepository) {
        this.storiesService = storiesService;
        this.pastStoriesRepository = pastStoriesRepository;
    }

    @Cacheable(CacheConfig.BEST_STORIES_CACHE)
    public List<Story> getBestStories(Integer limit) {
        List<Story> bestStories = storiesService.getBestStories();
        bestStories = rankByScore(bestStories);
        bestStories = applyLimit(bestStories, limit);

        addToDatabase(bestStories);

        return bestStories;
    }

    @Cacheable(CacheConfig.PAST_STORIES_CACHE)
    public List<Story> getPastStories(Integer limit) {
        List<Story> pastStories = storiesService.getPastStories();
        pastStories = applyLimit(pastStories, limit);
        return pastStories;
    }

    private void addToDatabase(List<Story> bestStories) {
        List<PastStory> pastStories = new ArrayList<>();
        bestStories.forEach(story -> pastStories.add(ConverterUtil.convert(story)));
        this.pastStoriesRepository.saveAll(pastStories);
        log.info("Entries saved to database successfully");
    }

    private List<Story> rankByScore(List<Story> bestStories) {
        Collections.sort(bestStories, (first, second) -> first.getScore().compareTo(second.getScore()) * -1);
        return bestStories;
    }

}
