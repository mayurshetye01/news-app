package com.questionpro.newsapp.services;

public interface CacheService {

    void evictAll();

    void evict(String cacheName);
}
