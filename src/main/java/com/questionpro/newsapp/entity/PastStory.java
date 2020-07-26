package com.questionpro.newsapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.net.URL;
import java.util.UUID;

@Data
@Entity
@Table(name = "past_stories")
public class PastStory {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "story_id")
    private String storyId;

    private String username;

    @Column(name = "time_of_submission")
    private Long timeOfSubmission;

    private URL url;

    private String title;

    private Long score;
}
