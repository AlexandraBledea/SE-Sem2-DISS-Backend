package com.project.diss.controller;

import com.project.diss.dto.*;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.TrainingDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.diss.util.AppValidator.validateTrainingDocumentCreation;
import static com.project.diss.util.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class TrainingDocumentController {

    public static final String TRAINING_DOCUMENT_BASE_URL = "/training-document";
    public final TrainingDocumentService documentService;

    @Autowired
    public TrainingDocumentController(TrainingDocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value =  TRAINING_DOCUMENT_BASE_URL + CREATE_TRAINING_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingDocumentDto> createTrainingDocument(@RequestBody TrainingDocumentSaveDto trainingDocument) throws RequestNotValidException, EntityNotFoundException {
        log.info("Start: Create training document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<TrainingDocumentDto> response;
        if (!validateTrainingDocumentCreation(trainingDocument)) {
            log.info("End due to error: Create training document. Timestamp: {}", LocalDateTime.now());
            throw new RequestNotValidException();
        } else {
            response = ResponseEntity.ok(documentService.createTrainingDocument(trainingDocument));
        }
        log.info("End: Create training document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = TRAINING_DOCUMENT_BASE_URL + GET_COMPLETED_TRAININGS_SUB_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrainingDocumentGetDto>> getCompletedTrainingDocuments(@PathVariable("id") Long id) {
        log.info("Start: Get completed training documents. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<TrainingDocumentGetDto>> response = ResponseEntity.ok(documentService.getCompletedTrainingDocuments(id));
        log.info("End: Get completed training documents. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = TRAINING_DOCUMENT_BASE_URL + GET_TODO_TRAININGS_SUB_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrainingDocumentGetDto>> getTodoTrainingDocuments(@PathVariable("id") Long id) {
        log.info("Start: Get todo training documents. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<TrainingDocumentGetDto>> response = ResponseEntity.ok(documentService.getTodoTrainingDocuments(id));
        log.info("End: Get todo training documents. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @PostMapping(value = TRAINING_DOCUMENT_BASE_URL + GET_TRAINING_SUB_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingDocumentViewDto> getTrainingDocument(@RequestBody TrainingDocumentStartDto dto) throws EntityNotFoundException {
        log.info("Start: Get training document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<TrainingDocumentViewDto> response = ResponseEntity.ok(documentService.getTrainingDocument(dto));
        log.info("End: Get training document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @PostMapping(value = TRAINING_DOCUMENT_BASE_URL + UPDATE_BADGE_SUB_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateBadgeTraining(@RequestBody BadgeDto badgeDto) throws EntityNotFoundException {
        log.info("Start: Start training document. Timestamp: {}", LocalDateTime.now());
        documentService.updateBadgeTraining(badgeDto);
        ResponseEntity<Object> response = ResponseEntity.ok().build();
        log.info("End: Start training document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @PutMapping(value = TRAINING_DOCUMENT_BASE_URL + UPDATE_USER_PROGRESS_SUB_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(@RequestBody UserProgressUpdateDto userDto) throws EntityNotFoundException {
        log.info("Start: Update user. Timestamp: {}", LocalDateTime.now());
        documentService.updateUserProgress(userDto);
        ResponseEntity<Object> response = ResponseEntity.ok().build();
        log.info("End: Update user. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @DeleteMapping(value = TRAINING_DOCUMENT_BASE_URL + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteTrainingDocument(@PathVariable Long id) throws EntityNotFoundException {
        log.info("Start: Delete training document with id {}. Timestamp: {}", id, LocalDateTime.now());
        documentService.deleteTrainingDocument(id);
        ResponseEntity<Object> response = ResponseEntity.ok().build();
        log.info("End: Delete training document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = TRAINING_DOCUMENT_BASE_URL + SEARCH_TRAINING_SUB_PATH + "/{searchKey}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrainingDocumentGetDto>> searchTrainingDocument(@PathVariable("searchKey") String searchKey) throws EntityNotFoundException {
        log.info("Start: Search training document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<TrainingDocumentGetDto>> response = ResponseEntity.ok(documentService.searchForTrainingDocuments(searchKey));
        log.info("End: Search training document. Timestamp: {}", LocalDateTime.now());
        return response;
    }
}
