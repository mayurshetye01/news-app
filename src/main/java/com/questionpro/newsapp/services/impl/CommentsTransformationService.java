package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.config.CacheConfig;
import com.questionpro.newsapp.model.Comment;
import com.questionpro.newsapp.model.User;
import com.questionpro.newsapp.services.AbstractTransformationService;
import com.questionpro.newsapp.services.CommentsService;
import com.questionpro.newsapp.services.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommentsTransformationService extends AbstractTransformationService<Comment> {
    private final CommentsService commentsService;
    private final UserService userService;

    public CommentsTransformationService(CommentsService commentsService, UserService userService) {
        this.commentsService = commentsService;
        this.userService = userService;
    }

    @Cacheable(CacheConfig.COMMENTS_CACHE)
    public List<Comment> getComments(String storyId, Integer limit) {
        List<Comment> comments = commentsService.getComments(storyId);
        comments = sortByNumberOfChildComments(comments);
        comments = applyLimit(comments, limit);
        comments = addUserProfileAgeInfo(comments);

        return comments;
    }

    private List<Comment> addUserProfileAgeInfo(List<Comment> comments) {
        comments.forEach(comment -> {
            User user = userService.getByUsername(comment.getUser());
            comment.setUserProfileAgeInYears(getUserProfileAge(user));
        });
        return comments;
    }

    private Double getUserProfileAge(User user) {
        Long createdOn = user.getCreatedOn();
        Long currentEpoch = Instant.now().getEpochSecond();
        Long daysPassed = TimeUnit.SECONDS.toDays(currentEpoch - createdOn);
        Double yearsPassed = daysPassed / 365.0;
        return Math.round(yearsPassed * 100.0) / 100.0;
    }

    private List<Comment> sortByNumberOfChildComments(List<Comment> comments) {
        Collections.sort(comments, (first, second) -> {
            if (first.getComments() == null && second.getComments() == null)
                return 0;
            if (first.getComments() == null)
                return 1;
            if (second.getComments() == null)
                return -1;
            return (first.getComments().length - second.getComments().length) * -1;
        });

        return comments;
    }
}
