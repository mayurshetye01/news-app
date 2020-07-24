package com.questionpro.newsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.net.URL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private String id;

    @JsonProperty("by")
    private String user;

    @JsonProperty("time")
    private Long timeOfSubmission;

    private URL url;

}
