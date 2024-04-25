package com.project.diss.controller;

import com.project.diss.dto.EmployeeDocumentDto;
import com.project.diss.dto.SaveEmployeeDocumentDto;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.EmployeeDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.diss.util.AppValidator.validateEmployeeDocumentCreation;
import static com.project.diss.util.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class EmployeeDocumentController {

    public static final String EMPLOYEE_DOCUMENT_BASE_URL = "/employee-document";
    public final EmployeeDocumentService documentService;

    @Autowired
    public EmployeeDocumentController(EmployeeDocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value =  EMPLOYEE_DOCUMENT_BASE_URL + CREATE_DOCUMENT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDocumentDto> createEmployeeDocument(@RequestBody SaveEmployeeDocumentDto employeeDocument) throws RequestNotValidException, EntityNotFoundException {
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

    @GetMapping(value = EMPLOYEE_DOCUMENT_BASE_URL + GET_OWN_DOCUMENTS_SUB_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDocumentDto>> getEmployeeOwnDocuments(@PathVariable Long id) throws EntityNotFoundException {
        log.info("Start: Get employee own documents. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<EmployeeDocumentDto>> response = ResponseEntity.ok(documentService.getEmployeeOwnDocuments(id));
        log.info("End: Get employee own documents. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = EMPLOYEE_DOCUMENT_BASE_URL + GET_DOCUMENTS_SUB_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDocumentDto>> getEmployeeDocuments(@PathVariable Long id) throws EntityNotFoundException {
        log.info("Start: Get employee documents. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<EmployeeDocumentDto>> response = ResponseEntity.ok(documentService.getEmployeeDocuments(id));
        log.info("End: Get employee documents. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = EMPLOYEE_DOCUMENT_BASE_URL + GET_DOCUMENT_SUB_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDocumentDto> getEmployeeDocument(@PathVariable Long id) throws EntityNotFoundException {
        log.info("Start: Get employee document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<EmployeeDocumentDto> response = ResponseEntity.ok(documentService.getEmployeeDocument(id));
        log.info("End: Get employee document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @DeleteMapping(value = EMPLOYEE_DOCUMENT_BASE_URL + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteEmployeeDocument(@PathVariable Long id) throws EntityNotFoundException {
        log.info("Start: Delete employee document. Timestamp: {}", LocalDateTime.now());
        documentService.deleteEmployeeDocument(id);
        ResponseEntity<Object> response = ResponseEntity.ok().build();
        log.info("End: Delete employee document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @PutMapping(value = EMPLOYEE_DOCUMENT_BASE_URL + UPDATE_DOCUMENT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDocumentDto> updateEmployeeDocument(@RequestBody SaveEmployeeDocumentDto employeeDocument) throws EntityNotFoundException {
        log.info("Start: Update employee document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<EmployeeDocumentDto> response = ResponseEntity.ok(documentService.updateEmployeeDocument(employeeDocument));
        log.info("End: Update employee document. Timestamp: {}", LocalDateTime.now());
        return response;
    }
}
