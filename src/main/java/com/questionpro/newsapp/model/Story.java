package com.questionpro.newsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story extends Item {
    private String title;
    private Long score;
    private URL url;
}
