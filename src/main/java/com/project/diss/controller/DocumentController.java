package com.project.diss.controller;

import com.project.diss.controller.dto.EmployeeDocumentDto;
import com.project.diss.exception.CustomException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.project.diss.util.AppValidator.validateEmployeeDocumentCreation;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class DocumentController {

    public static final String EMPLOYEE_DOCUMENT_BASE_URL = "/employee-document";

    public static final String CREATE_DOCUMENT_SUB_PATH = "/create-document";

    public final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value =  EMPLOYEE_DOCUMENT_BASE_URL + CREATE_DOCUMENT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDocumentDto> createEmployeeDocument(@RequestBody EmployeeDocumentDto employeeDocument) throws RequestNotValidException, CustomException {
        log.info("Start: Create employee document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<EmployeeDocumentDto> response;
        if (!validateEmployeeDocumentCreation(employeeDocument)) {
            log.info("End due to error: Create employee document. Timestamp: {}", LocalDateTime.now());
            throw new RequestNotValidException();
        } else {
            response = ResponseEntity.ok(documentService.createEmployeeDocument(employeeDocument));
        }
        log.info("End: Create employee document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

}
