package com.questionpro.newsapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    private String id;

    @JsonProperty("created")
    Long createdOn;
}
