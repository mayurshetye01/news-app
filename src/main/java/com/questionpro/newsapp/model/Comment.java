package com.questionpro.newsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment extends Item {
    private String text;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double userProfileAgeInYears;
}
