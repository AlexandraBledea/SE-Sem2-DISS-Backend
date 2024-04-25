package com.project.diss.controller;

import com.project.diss.dto.TrainingDocumentDto;
import com.project.diss.dto.SaveTrainingDocumentDto;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.TrainingDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value =  TRAINING_DOCUMENT_BASE_URL + CREATE_DOCUMENT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainingDocumentDto> createTrainingDocument(@RequestBody SaveTrainingDocumentDto trainingDocument) throws RequestNotValidException, EntityNotFoundException {
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

    @GetMapping(value = TRAINING_DOCUMENT_BASE_URL + GET_DOCUMENTS_SUB_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrainingDocumentDto>> getTrainingDocuments() {
        log.info("Start: Get training documents. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<TrainingDocumentDto>> response = ResponseEntity.ok(documentService.getTrainingDocuments());
        log.info("End: Get training documents. Timestamp: {}", LocalDateTime.now());
        return response;
    }

}
