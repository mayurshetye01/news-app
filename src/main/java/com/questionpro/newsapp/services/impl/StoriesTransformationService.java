package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.model.Story;
import com.questionpro.newsapp.services.AbstractTransformationService;
import com.questionpro.newsapp.services.StoriesService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StoriesTransformationService extends AbstractTransformationService {
    private final StoriesService storiesService;

    public StoriesTransformationService(StoriesService storiesService) {
        this.storiesService = storiesService;
    }

    public List<Story> getBestStories(Integer limit) {
        List<Story> bestStories = storiesService.getBestStories();
        bestStories = rankByScore(bestStories);
        bestStories = applyLimit(bestStories, limit);

        return bestStories;
    }

    public List<Story> getPastStories(Integer limit) {
        List<Story> pastStories = storiesService.getBestStories();
        pastStories = applyLimit(pastStories, limit);
        return pastStories;
    }

    private List<Story> rankByScore(List<Story> bestStories) {
        Collections.sort(bestStories, (first, second) -> first.getScore().compareTo(second.getScore()) * -1);
        return bestStories;
    }

}
