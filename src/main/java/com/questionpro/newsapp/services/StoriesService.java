package com.questionpro.newsapp.services;

import com.questionpro.newsapp.model.Story;

import java.util.List;

public interface StoriesService {

    Story get(String storyId);

    List<Story> getBestStories();

    List<Story> getPastStories();
}
