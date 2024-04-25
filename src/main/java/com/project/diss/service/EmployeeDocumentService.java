package com.project.diss.service;

import com.project.diss.converters.FileConverter;
import com.project.diss.dto.EmployeeDocumentDto;
import com.project.diss.converters.EmployeeDocumentConverter;
import com.project.diss.dto.FileDto;
import com.project.diss.dto.SaveEmployeeDocumentDto;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.persistance.DocumentRepository;
import com.project.diss.persistance.EmployeeDocumentRepository;
import com.project.diss.persistance.FileRepository;
import com.project.diss.persistance.UserRepository;
import com.project.diss.persistance.entity.DocumentEntity;
import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import com.project.diss.persistance.entity.FileEntity;
import com.project.diss.persistance.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeDocumentService {


    UserRepository userRepository;
    DocumentRepository documentRepository;
    FileRepository fileRepository;
    EmployeeDocumentRepository employeeDocumentRepository;

    EmployeeDocumentConverter documentConverter;
    FileConverter fileConverter;

    @Autowired
    public EmployeeDocumentService(UserRepository userRepository, DocumentRepository documentRepository,
                                   EmployeeDocumentRepository employeeDocumentRepository, FileRepository fileRepository,
                                   EmployeeDocumentConverter documentConverter, FileConverter fileConverter) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.employeeDocumentRepository = employeeDocumentRepository;
        this.documentConverter = documentConverter;
        this.fileConverter = fileConverter;
        this.fileRepository = fileRepository;
    }

    public FileEntity saveFile(FileDto fileDto) {
        if (fileDto == null) {
            return null;
        } else {
            FileEntity fileEntity = fileConverter.convertFileDtoToFileEntity(fileDto);
            return fileRepository.save(fileEntity);
        }
    }

    public FileEntity updateFile(FileDto newFile, FileEntity oldFile) {
        FileEntity fileEntity = fileConverter.convertFileDtoToFileEntity(newFile);
        return fileRepository.save(fileEntity);
    }

    public EmployeeDocumentDto createEmployeeDocument(SaveEmployeeDocumentDto employeeDocument) throws EntityNotFoundException {
        log.info("Creating employee document: {}", employeeDocument);
        Optional<UserEntity> user = userRepository.findById(employeeDocument.getUserId());
        FileEntity file = this.saveFile(employeeDocument.getFile());
        if (user.isPresent()) {
            EmployeeDocumentEntity employeeDocumentEntity = documentConverter.convertSaveEmployeeDocumentDtoToEmployeeDocumentEntity(employeeDocument, user.get(), file);
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
        if (employeeDocumentEntity.isEmpty()) {
            log.info("Could not find employee document with id {}", id);
            throw new EntityNotFoundException();
        }
        employeeDocumentRepository.deleteById(id);
    }

    public EmployeeDocumentDto updateEmployeeDocument(SaveEmployeeDocumentDto employeeDocument) throws EntityNotFoundException {
        Optional<EmployeeDocumentEntity> employeeDocumentEntity = employeeDocumentRepository.findById(employeeDocument.getId());
        if (employeeDocumentEntity.isEmpty()) {
            log.info("Could not find employee document with id {}", employeeDocument.getId());
            throw new EntityNotFoundException();
        }

        FileEntity oldFile = employeeDocumentEntity.get().getFile();
        FileDto newFile = employeeDocument.getFile();

        if(newFile == null) {
            EmployeeDocumentEntity updatedEmployeeDocumentEntity = documentConverter.convertSaveEmployeeDocumentDtoToEmployeeDocumentEntity(employeeDocument, employeeDocumentEntity.get().getUser(), null);
            updatedEmployeeDocumentEntity = employeeDocumentRepository.save(updatedEmployeeDocumentEntity);
            if (oldFile != null) {
                this.fileRepository.deleteById(oldFile.getId());
            }
            return documentConverter.convertEmployeeDocumentEntityToEmployeeDocumentDto(updatedEmployeeDocumentEntity);
        }

        FileEntity file = this.updateFile(employeeDocument.getFile(), employeeDocumentEntity.get().getFile());
        EmployeeDocumentEntity updatedEmployeeDocumentEntity = documentConverter.convertSaveEmployeeDocumentDtoToEmployeeDocumentEntity(employeeDocument, employeeDocumentEntity.get().getUser(), file);
        return documentConverter.convertEmployeeDocumentEntityToEmployeeDocumentDto(employeeDocumentRepository.save(updatedEmployeeDocumentEntity));
    }
}
