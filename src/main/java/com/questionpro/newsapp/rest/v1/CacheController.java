package com.questionpro.newsapp.rest.v1;

import com.questionpro.newsapp.services.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.questionpro.newsapp.common.Constants.V1_API_URL;

@RestController
@RequestMapping(V1_API_URL)
@Slf4j
public class CacheController {

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @RequestMapping(
            value = "/cache/content",
            method = RequestMethod.DELETE)
    public ResponseEntity clear() {
        cacheService.evictAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
