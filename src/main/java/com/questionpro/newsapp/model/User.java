package com.questionpro.newsapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty("id")
    private String username;

    @JsonProperty("created")
    Long createdOn;
}
