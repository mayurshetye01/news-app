package com.questionpro.newsapp.services;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractTransformationService<T> {

    protected List<T> applyLimit(List<T> result, Integer limit) {
        return limit != null && limit <= result.size() ? result.stream().limit(limit).collect(Collectors.toList()) : result;
    }

}
