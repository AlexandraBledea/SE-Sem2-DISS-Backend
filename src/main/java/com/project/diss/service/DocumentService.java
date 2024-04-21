package com.project.diss.service;

import com.project.diss.controller.dto.EmployeeDocumentDto;
import com.project.diss.converters.DocumentConverter;
import com.project.diss.exception.CustomException;
import com.project.diss.persistance.DocumentRepository;
import com.project.diss.persistance.EmployeeDocumentRepository;
import com.project.diss.persistance.UserRepository;
import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DocumentService {

    UserRepository userRepository;
    DocumentRepository documentRepository;
    EmployeeDocumentRepository employeeDocumentRepository;

    DocumentConverter documentConverter;

    @Autowired
    public DocumentService(UserRepository userRepository, DocumentRepository documentRepository, EmployeeDocumentRepository employeeDocumentRepository, DocumentConverter documentConverter) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.employeeDocumentRepository = employeeDocumentRepository;
        this.documentConverter = documentConverter;
    }

    public EmployeeDocumentDto createEmployeeDocument(EmployeeDocumentDto employeeDocument) throws CustomException {
        log.info("Creating employee document: {}", employeeDocument);
        Optional<UserEntity> user = userRepository.findById(employeeDocument.getUserId());
        if (user.isPresent()) {
            EmployeeDocumentEntity employeeDocumentEntity = documentConverter.convertEmployeeDocumentDtoToEmployeeDocumentEntity(employeeDocument, user.get());
            documentRepository.save(employeeDocumentEntity.getDocument());
            employeeDocumentEntity = employeeDocumentRepository.save(employeeDocumentEntity);
            return documentConverter.convertEmployeeDocumentEntityToEmployeeDocumentDto(employeeDocumentEntity);
        }
        log.error("Could not save database entry because user with id {} doesn't exist", employeeDocument.getUserId());
        throw new CustomException("USER NOT FOUND", "Cannot create document!");
    }
}
