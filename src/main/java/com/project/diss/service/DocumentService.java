package com.project.diss.service;

import com.project.diss.controller.dto.EmployeeDocumentDto;
import com.project.diss.controller.dto.TrainingDocumentDto;
import com.project.diss.converters.DocumentConverter;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.persistance.DocumentRepository;
import com.project.diss.persistance.EmployeeDocumentRepository;
import com.project.diss.persistance.TrainingDocumentRepository;
import com.project.diss.persistance.UserRepository;
import com.project.diss.persistance.entity.DocumentEntity;
import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import com.project.diss.persistance.entity.TrainingDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DocumentService {

    UserRepository userRepository;
    DocumentRepository documentRepository;
    EmployeeDocumentRepository employeeDocumentRepository;

    TrainingDocumentRepository trainingDocumentRepository;

    DocumentConverter documentConverter;

    @Autowired
    public DocumentService(UserRepository userRepository, DocumentRepository documentRepository, EmployeeDocumentRepository employeeDocumentRepository, TrainingDocumentRepository trainingDocumentRepository, DocumentConverter documentConverter) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.employeeDocumentRepository = employeeDocumentRepository;
        this.documentConverter = documentConverter;
        this.trainingDocumentRepository = trainingDocumentRepository;
    }

    public TrainingDocumentDto createTrainingDocument(TrainingDocumentDto trainingDocument) throws EntityNotFoundException {
        log.info("Creating training document: {}", trainingDocument);
        Optional<UserEntity> user = userRepository.findById(trainingDocument.getUserId());
        if (user.isPresent()) {
            TrainingDocumentEntity trainingDocumentEntity = documentConverter.convertTrainingDocumentDtoToTrainingDocumentEntity(trainingDocument, user.get());
            trainingDocumentEntity = trainingDocumentRepository.save(trainingDocumentEntity);
            return documentConverter.convertTrainingDocumentEntityToTrainingDocumentDto(trainingDocumentEntity);
        }
        log.error("Could not save database entry because user with id {} doesn't exist", trainingDocument.getUserId());
        throw new EntityNotFoundException();
    }

    public List<TrainingDocumentDto> getTrainingDocuments() {
        List<TrainingDocumentEntity> trainingDocumentEntities = trainingDocumentRepository.findAll();
        return documentConverter.convertTrainingDocumentEntitiesToTrainingDocumentDtos(trainingDocumentEntities);
    }

    public EmployeeDocumentDto createEmployeeDocument(EmployeeDocumentDto employeeDocument) throws EntityNotFoundException {
        log.info("Creating employee document: {}", employeeDocument);
        Optional<UserEntity> user = userRepository.findById(employeeDocument.getUserId());
        if (user.isPresent()) {
            EmployeeDocumentEntity employeeDocumentEntity = documentConverter.convertEmployeeDocumentDtoToEmployeeDocumentEntity(employeeDocument, user.get());
            employeeDocumentEntity = employeeDocumentRepository.save(employeeDocumentEntity);
            return documentConverter.convertEmployeeDocumentEntityToEmployeeDocumentDto(employeeDocumentEntity);
        }
        log.error("Could not save database entry because user with id {} doesn't exist", employeeDocument.getUserId());
        throw new EntityNotFoundException();
    }

    public List<EmployeeDocumentDto> getEmployeeOwnDocuments(Long id) throws EntityNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<DocumentEntity> documents = user.get().getDocuments();
            List<EmployeeDocumentEntity> employeeDocumentEntities = documents.stream().filter(document -> document instanceof EmployeeDocumentEntity).map(document -> (EmployeeDocumentEntity) document).toList();
            return documentConverter.convertEmployeeDocumentEntitiesToEmployeeDocumentDtos(employeeDocumentEntities);
        }
        log.error("Could not retrieve employee documents for user with id {}", id);
        throw new EntityNotFoundException();
    }

    public List<EmployeeDocumentDto> getEmployeeDocuments(Long id) throws EntityNotFoundException {
        List<DocumentEntity> documents = documentRepository.findAll();
        List<EmployeeDocumentDto> employeeDocumentEntities = documentConverter
                .convertEmployeeDocumentEntitiesToEmployeeDocumentDtos(documents.stream()
                        .filter(document -> document instanceof EmployeeDocumentEntity &&
                                ((EmployeeDocumentEntity) document).getVisibility() &&
                                !document.getUser().getId().equals(id))
                        .map(document -> (EmployeeDocumentEntity) document).toList());
        List<EmployeeDocumentDto> employeeOwnDocumentEntities = getEmployeeOwnDocuments(id);
        employeeDocumentEntities.addAll(employeeOwnDocumentEntities);
        employeeDocumentEntities.sort((o1, o2) -> o2.getLastModified().compareTo(o1.getLastModified()));
        return employeeDocumentEntities;
    }

    public EmployeeDocumentDto getEmployeeDocument(Long id) throws EntityNotFoundException {
        Optional<EmployeeDocumentEntity> employeeDocumentEntity = employeeDocumentRepository.findById(id);
        if (employeeDocumentEntity.isPresent()) {
            return documentConverter.convertEmployeeDocumentEntityToEmployeeDocumentDto(employeeDocumentEntity.get());
        }
        log.error("Could not retrieve employee document with id {}", id);
        throw new EntityNotFoundException();
    }

    public void deleteEmployeeDocument(Long id) throws EntityNotFoundException {
        Optional<EmployeeDocumentEntity> employeeDocumentEntity = employeeDocumentRepository.findById(id);
        if(employeeDocumentEntity.isEmpty()){
            log.info("Could not find employee document with id {}", id);
            throw new EntityNotFoundException();
        }
        employeeDocumentRepository.deleteById(id);
    }

    public EmployeeDocumentDto updateEmployeeDocument(EmployeeDocumentDto employeeDocument) throws EntityNotFoundException {
        Optional<EmployeeDocumentEntity> employeeDocumentEntity = employeeDocumentRepository.findById(employeeDocument.getId());
        if(employeeDocumentEntity.isEmpty()){
            log.info("Could not find employee document with id {}", employeeDocument.getId());
            throw new EntityNotFoundException();
        }
        EmployeeDocumentEntity updatedEmployeeDocumentEntity = documentConverter.convertEmployeeDocumentDtoToEmployeeDocumentEntity(employeeDocument, employeeDocumentEntity.get().getUser());
        return documentConverter.convertEmployeeDocumentEntityToEmployeeDocumentDto(employeeDocumentRepository.save(updatedEmployeeDocumentEntity));
    }
}
