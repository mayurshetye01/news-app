package com.questionpro.newsapp.services.impl;

import com.questionpro.newsapp.services.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheServiceImpl implements CacheService {
    private final CacheManager cacheManager;

    public CacheServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void evictAll() {
        cacheManager.getCacheNames().forEach(cache -> cacheManager.getCache(cache).clear());
        log.info("All caches evicted");
    }

    @Override
    public void evict(String cacheName) {
        if (cacheName == null || cacheName.isEmpty()) {
            log.warn("Empty cache name to evict!");
            return;
        }
        this.cacheManager.getCache(cacheName).clear();
        log.debug("Cache {} evicted", cacheName);
    }
}
