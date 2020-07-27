package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.config.ApplicationParameters;
import com.questionpro.newsapp.model.Comment;
import com.questionpro.newsapp.model.Story;
import com.questionpro.newsapp.services.AbstractItemService;
import com.questionpro.newsapp.services.CommentsService;
import com.questionpro.newsapp.services.StoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class CommentsServiceImpl extends AbstractItemService<Comment> implements CommentsService {
    private final StoriesService storiesService;

    protected CommentsServiceImpl(ApplicationParameters applicationParameters, RestTemplate restTemplate, StoriesService storiesService) {
        super(applicationParameters, restTemplate);
        this.storiesService = storiesService;
    }

    @Override
    public List<Comment> getComments(String storyId) {
        Story story = storiesService.get(storyId);
        String[] commentIds = story.getComments();

        log.info("Getting comments for story id {}", storyId);
        List<Comment> comments = getItems(commentIds, Comment.class);

        return comments;
    }
}
