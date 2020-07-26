package com.questionpro.newsapp.rest.v1;

import com.questionpro.newsapp.model.Comment;
import com.questionpro.newsapp.services.impl.CommentsTransformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.questionpro.newsapp.common.Constants.V1_API_URL;

@RestController
@RequestMapping(V1_API_URL)
@Slf4j
public class CommentsController {
    private final CommentsTransformationService commentsTransformationService;

    @Autowired
    public CommentsController(CommentsTransformationService commentsTransformationService) {
        this.commentsTransformationService = commentsTransformationService;
    }

    @RequestMapping(value = "/comments/{storyId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable String storyId,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<Comment> comments = commentsTransformationService.getComments(storyId, limit);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
