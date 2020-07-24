package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.model.Comment;
import com.questionpro.newsapp.services.CommentsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Override
    public List<Comment> getComments(String storyId) {
        return null;
    }
}
