package com.questionpro.newsapp.config;

import com.questionpro.newsapp.services.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
public class CacheConfig {

    private final CacheService cacheService;
    public static final String BEST_STORIES_CACHE = "best-stories";
    public static final String PAST_STORIES_CACHE = "past-stories";
    public static final String COMMENTS_CACHE = "comments";

    public CacheConfig(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Scheduled(fixedRateString = "${cache.eviction-time}")
    public void evictCache() {
        cacheService.evictAll();
        log.info("Cache evicted");
    }
}
