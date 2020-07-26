package com.questionpro.newsapp.util;

import com.questionpro.newsapp.entity.PastStory;
import com.questionpro.newsapp.model.Story;

public abstract class ConverterUtil {

    public static Story convert(PastStory entity) {
        Story story = new Story();
        story.setScore(entity.getScore());
        story.setTitle(entity.getTitle());
        story.setId(entity.getStoryId());
        story.setTimeOfSubmission(entity.getTimeOfSubmission());
        story.setUrl(entity.getUrl());
        story.setUser(entity.getUsername());

        return story;
    }

    public static PastStory convert(Story dto){
        PastStory pastStory = new PastStory();
        pastStory.setScore(dto.getScore());
        pastStory.setTitle(dto.getTitle());
        pastStory.setStoryId(dto.getId());
        pastStory.setTimeOfSubmission(dto.getTimeOfSubmission());
        pastStory.setUrl(dto.getUrl());
        pastStory.setUsername(dto.getUser());

        return pastStory;
    }
}
