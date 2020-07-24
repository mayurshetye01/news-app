package com.questionpro.newsapp.services;

import com.questionpro.newsapp.model.Comment;

import java.util.List;

public interface CommentsService {

    List<Comment> getComments(String storyId);
}
