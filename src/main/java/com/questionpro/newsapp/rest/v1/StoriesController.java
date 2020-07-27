package com.questionpro.newsapp.rest.v1;

import com.questionpro.newsapp.model.Story;
import com.questionpro.newsapp.services.impl.transformation.StoriesTransformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.questionpro.newsapp.common.Constants.V1_API_URL;

@RestController
@RequestMapping(V1_API_URL)
@Slf4j
public class StoriesController {
    private final StoriesTransformationService storiesTransformationService;

    @Autowired
    public StoriesController(StoriesTransformationService storiesTransformationService) {
        this.storiesTransformationService = storiesTransformationService;
    }

    @RequestMapping(value = "/best-stories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Story>> getBestStories(
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        log.debug("Request to get best stories received");
        List<Story> bestStories = storiesTransformationService.getBestStories(limit);
        return new ResponseEntity<>(bestStories, HttpStatus.OK);
    }

    @RequestMapping(value = "/past-stories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Story>> getPastStories(
            @RequestParam(value = "limit", required = false) Integer limit) {
        log.debug("Request to get past stories received");
        List<Story> pastStories = storiesTransformationService.getPastStories(limit);
        return new ResponseEntity<>(pastStories, HttpStatus.OK);
    }
}
