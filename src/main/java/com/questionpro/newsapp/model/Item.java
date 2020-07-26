package com.questionpro.newsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String id;

    @JsonProperty("by")
    private String user;

    @JsonProperty("time")
    private Long timeOfSubmission;

    @JsonProperty(value = "kids", access = JsonProperty.Access.WRITE_ONLY)
    private String[] comments;
}
