package com.project.diss.controller;

import com.project.diss.dto.*;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.diss.util.AppValidator.validateCommentCreation;
import static com.project.diss.util.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class CommentController {

    public static final String COMMENT_BASE_URL = "/comments";
    public final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value =  COMMENT_BASE_URL + CREATE_COMMENT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentSaveDto comment) throws RequestNotValidException, EntityNotFoundException {
        log.info("Start: Create comment. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<CommentDto> response;
        if (!validateCommentCreation(comment)) {
            log.info("End due to error: Create comment. Timestamp: {}", LocalDateTime.now());
            throw new RequestNotValidException();
        } else {
            response = ResponseEntity.ok(commentService.createComment(comment));
        }
        log.info("End: Create comment. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value =  COMMENT_BASE_URL + GET_COMMENTS_SUB_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentDto>> getDocumentComments(@PathVariable("id") Long id) throws RequestNotValidException, EntityNotFoundException {
        log.info("Start: Get comments for document id {}. Timestamp: {}", id, LocalDateTime.now());
        ResponseEntity<List<CommentDto>> response = ResponseEntity.ok(commentService.getDocumentComments(id));
        log.info("End: Get comments for document id {}. Timestamp: {}", id, LocalDateTime.now());
        return response;
    }

    @DeleteMapping(value =  COMMENT_BASE_URL + GET_COMMENTS_SUB_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteComment(@PathVariable("id") Long id) throws EntityNotFoundException, RequestNotValidException {
        log.info("Start: Delete comment with id {}. Timestamp: {}", id, LocalDateTime.now());
        commentService.deleteComment(id);
        ResponseEntity<Object> response = ResponseEntity.ok().build();
        log.info("End: Delete comment. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @PutMapping(value = COMMENT_BASE_URL + UPDATE_COMMENT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentUpdateDto comment) throws EntityNotFoundException, RequestNotValidException {
        log.info("Start: Update comment. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<CommentDto> response = ResponseEntity.ok(commentService.updateComment(comment));
        log.info("End: Update comment. Timestamp: {}", LocalDateTime.now());
        return response;
    }
}
