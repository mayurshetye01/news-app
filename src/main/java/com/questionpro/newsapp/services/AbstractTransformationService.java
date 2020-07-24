package com.questionpro.newsapp.services;

import java.util.List;

public abstract class AbstractTransformationService<T> {

    protected List<T> applyLimit(List<T> result, Integer limit) {
        return limit != null ? result.subList(0, limit) : result;
    }

}
