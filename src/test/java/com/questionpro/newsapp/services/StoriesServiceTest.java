package com.questionpro.newsapp.services;

import com.questionpro.newsapp.entity.PastStory;
import com.questionpro.newsapp.model.Story;
import com.questionpro.newsapp.repository.PastStoriesRepository;
import com.questionpro.newsapp.services.impl.StoriesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoriesServiceTest extends BaseServiceTest {

    @Mock
    PastStoriesRepository pastStoriesRepository;

    @InjectMocks
    StoriesServiceImpl storiesService;

    private static final String TEST_USER_USERNAME = "test-user";
    private static final String TEST_STORY_ID = "test-id";
    private static final String TEST_STORY_TITLE = "test-title";

    @Test
    void testGetStory() {
        when(applicationParameters.getHackerNewsBaseURL()).thenReturn("http://test-host.com/");
        when(applicationParameters.getItemEndpoint()).thenReturn("test-item");
        when(restTemplate.getForObject(any(URI.class), eq(Story.class))).thenReturn(getTestStory());

        Story result = storiesService.get(TEST_STORY_ID);

        verify(restTemplate, times(1)).getForObject(any(URI.class), eq(Story.class));

        assertEquals(TEST_STORY_TITLE, result.getTitle());
        assertEquals(TEST_STORY_ID, result.getId());
        assertEquals(TEST_USER_USERNAME, result.getUser());
    }

    @Test
    void testGetBestStories() {
        String[] testStoryIds = getTestStoryIds();

        when(applicationParameters.getHackerNewsBaseURL()).thenReturn("http://test-host.com/");
        when(applicationParameters.getBestStoriesEndpoint()).thenReturn("best-stories");
        when(applicationParameters.getThreadPoolSize()).thenReturn(1);

        when(restTemplate.getForObject(any(URI.class), eq(String[].class))).thenReturn(testStoryIds);
        when(restTemplate.getForObject(any(URI.class), eq(Story.class))).thenReturn(getTestStory());

        List<Story> result = storiesService.getBestStories();

        verify(restTemplate, times(1)).getForObject(any(URI.class), eq(String[].class));
        verify(restTemplate, times(testStoryIds.length)).getForObject(any(URI.class), eq(Story.class));

        assertEquals(result.size(), testStoryIds.length);
    }

    @Test
    void testGetPastStories() {
        List<PastStory> testPastStories = getTestPastStories();

        when(pastStoriesRepository.findAll()).thenReturn(testPastStories);
        List<Story> result = storiesService.getPastStories();

        verify(pastStoriesRepository, times(1)).findAll();
        assertEquals(testPastStories.size(), result.size());

    }

    private List<PastStory> getTestPastStories() {
        List<PastStory> pastStories = new ArrayList<>();

        PastStory story1 = new PastStory();
        story1.setUsername(TEST_USER_USERNAME + "_1");
        story1.setStoryId(TEST_STORY_ID + "_1");
        story1.setTimeOfSubmission(Instant.now().getEpochSecond());
        story1.setTitle(TEST_STORY_TITLE + "_1");

        PastStory story2 = new PastStory();
        story2.setUsername(TEST_USER_USERNAME + "_2");
        story2.setStoryId(TEST_STORY_ID + "_2");
        story2.setTimeOfSubmission(Instant.now().getEpochSecond());
        story2.setTitle(TEST_STORY_TITLE + "_2");

        pastStories.add(story1);
        pastStories.add(story2);

        return pastStories;
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

    private String[] getTestStoryIds() {
        String[] storyIds = new String[5];
        storyIds[0] = "story-0";
        storyIds[1] = "story-1";
        storyIds[2] = "story-2";
        storyIds[3] = "story-3";
        storyIds[4] = "story-4";

        return storyIds;
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
