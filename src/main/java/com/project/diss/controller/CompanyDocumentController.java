package com.project.diss.controller;

import com.project.diss.dto.CompanyDocumentDto;
import com.project.diss.dto.CompanyDocumentGetDto;
import com.project.diss.dto.CompanyDocumentSaveDto;
import com.project.diss.dto.EmployeeDocumentDto;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.exception.RequestNotValidException;
import com.project.diss.service.CompanyDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.diss.util.AppValidator.validateCompanyDocumentCreation;
import static com.project.diss.util.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class CompanyDocumentController {
    public static final String COMPANY_DOCUMENT_BASE_URL = "/company-document";
    public final CompanyDocumentService documentService;

    @Autowired
    public CompanyDocumentController(CompanyDocumentService documentService) {
        this.documentService = documentService;
    }

    // TODO - Add authorization
    @PostMapping(value = COMPANY_DOCUMENT_BASE_URL + CREATE_DOCUMENT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDocumentDto> createCompanyDocument(@RequestBody CompanyDocumentSaveDto companyDocument) throws RequestNotValidException, EntityNotFoundException {
        log.info("Start: Create company document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<CompanyDocumentDto> response;
        if (!validateCompanyDocumentCreation(companyDocument)) {
            log.info("End due to error: Create company document. Timestamp: {}", LocalDateTime.now());
            throw new RequestNotValidException();
        } else {
            response = ResponseEntity.ok(documentService.createCompanyDocument(companyDocument));
        }
        log.info("End: Create company document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = COMPANY_DOCUMENT_BASE_URL + GET_DOCUMENTS_SUB_PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyDocumentGetDto>> getCompanyDocuments() throws EntityNotFoundException {
        log.info("Start: Get company documents. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<List<CompanyDocumentGetDto>> response = ResponseEntity.ok(documentService.getCompanyDocuments());
        log.info("End: Get company documents. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @GetMapping(value = COMPANY_DOCUMENT_BASE_URL + GET_DOCUMENT_SUB_PATH + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDocumentDto> getCompanyDocument(@PathVariable Long id) throws EntityNotFoundException {
        log.info("Start: Get employee document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<CompanyDocumentDto> response = ResponseEntity.ok(documentService.getCompanyDocument(id));
        log.info("End: Get employee document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    @DeleteMapping(value = COMPANY_DOCUMENT_BASE_URL + "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDocumentDto> deleteCompanyDocument(@PathVariable Long id) throws EntityNotFoundException {
        log.info("Start: Delete company document. Timestamp: {}", LocalDateTime.now());
        documentService.deleteCompanyDocument(id);
        ResponseEntity<CompanyDocumentDto> response = ResponseEntity.ok().build();
        log.info("End: Delete company document. Timestamp: {}", LocalDateTime.now());
        return response;
    }

    // TODO - Add authorization
    @PutMapping(value = COMPANY_DOCUMENT_BASE_URL + UPDATE_DOCUMENT_SUB_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDocumentDto> updateCompanyDocument(@RequestBody CompanyDocumentSaveDto companyDocument) throws EntityNotFoundException {
        log.info("Start: Update company document. Timestamp: {}", LocalDateTime.now());
        ResponseEntity<CompanyDocumentDto> response = ResponseEntity.ok(documentService.updateCompanyDocument(companyDocument));
        log.info("End: Update company document. Timestamp: {}", LocalDateTime.now());
        return response;
    }
}
