package com.questionpro.newsapp.services;

import com.questionpro.newsapp.model.Comment;
import com.questionpro.newsapp.model.Story;
import com.questionpro.newsapp.services.impl.CommentsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceTest extends BaseServiceTest {

    @Mock
    StoriesService storiesService;

    @InjectMocks
    CommentsServiceImpl commentsService;

    private static final String TEST_USER_USERNAME = "test-user";
    private static final String TEST_STORY_ID = "test-id";
    private static final String TEST_STORY_TITLE = "test-title";
    private static final String TEST_COMMENT_TEXT = "test-comment";
    private static final String TEST_COMMENT_ID = "test-comment-id";


    @Test
    void testGetComments() {
        Story testStory = getTestStory();

        when(applicationParameters.getHackerNewsBaseURL()).thenReturn("http://test-host.com/");
        when(applicationParameters.getItemEndpoint()).thenReturn("test-item");
        when(applicationParameters.getThreadPoolSize()).thenReturn(1);

        when(storiesService.get(any(String.class))).thenReturn(testStory);
        when(restTemplate.getForObject(any(URI.class), eq(Comment.class))).thenReturn(getTestComment());

        List<Comment> result = commentsService.getComments(TEST_STORY_ID);

        verify(restTemplate, times(testStory.getComments().length)).getForObject(any(URI.class), eq(Comment.class));

        assertEquals(testStory.getComments().length, result.size());
    }

    private Comment getTestComment() {
        Comment comment = new Comment();
        comment.setUserProfileAgeInYears(1.0);
        comment.setUser(TEST_USER_USERNAME);
        comment.setText(TEST_COMMENT_TEXT);
        comment.setId(TEST_COMMENT_ID);

        return comment;
    }

    private Story getTestStory() {
        Story story = new Story();
        story.setUser(TEST_USER_USERNAME);
        story.setId(TEST_STORY_ID);
        story.setTimeOfSubmission(Instant.now().getEpochSecond());
        story.setTitle(TEST_STORY_TITLE);
        story.setComments(getTestCommentIds());

        return story;
    }

    private String[] getTestCommentIds() {
        String[] commentIds = new String[5];
        commentIds[0] = "comment-0";
        commentIds[1] = "comment-1";
        commentIds[2] = "comment-2";
        commentIds[3] = "comment-3";
        commentIds[4] = "comment-4";

        return commentIds;
    }
}
