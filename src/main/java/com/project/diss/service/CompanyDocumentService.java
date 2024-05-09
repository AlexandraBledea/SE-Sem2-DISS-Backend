package com.project.diss.service;

import com.project.diss.converters.CompanyDocumentConverter;
import com.project.diss.converters.FileConverter;
import com.project.diss.dto.CompanyDocumentDto;
import com.project.diss.dto.CompanyDocumentGetDto;
import com.project.diss.dto.CompanyDocumentSaveDto;
import com.project.diss.dto.FileDto;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.persistance.CompanyDocumentRepository;
import com.project.diss.persistance.DocumentRepository;
import com.project.diss.persistance.FileRepository;
import com.project.diss.persistance.UserRepository;
import com.project.diss.persistance.entity.CompanyDocumentEntity;
import com.project.diss.persistance.entity.FileEntity;
import com.project.diss.persistance.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompanyDocumentService {
    UserRepository userRepository;
    DocumentRepository documentRepository;
    FileRepository fileRepository;
    CompanyDocumentRepository companyDocumentRepository;
    FileConverter fileConverter;
    CompanyDocumentConverter companyDocumentConverter;

    @Autowired
    public CompanyDocumentService(UserRepository userRepository, DocumentRepository documentRepository, CompanyDocumentRepository companyDocumentRepository, FileRepository fileRepository, CompanyDocumentConverter companyDocumentConverter, FileConverter fileConverter) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.companyDocumentRepository = companyDocumentRepository;
        this.companyDocumentConverter = companyDocumentConverter;
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

    public FileEntity updateFile(FileDto newFile) {
        FileEntity fileEntity = fileConverter.convertFileDtoToFileEntity(newFile);
        return fileRepository.save(fileEntity);
    }

    public CompanyDocumentDto createCompanyDocument(CompanyDocumentSaveDto companyDocumentSaveDto) throws EntityNotFoundException {
        log.info("Creating company document: {}", companyDocumentSaveDto);
        Optional<UserEntity> user = userRepository.findById(companyDocumentSaveDto.getUserId());
        FileEntity file = saveFile(companyDocumentSaveDto.getFile());
        if (user.isPresent()) {
            CompanyDocumentEntity companyDocumentEntity = companyDocumentConverter.convertSaveCompanyDocumentDtoToCompanyDocumentEntity(companyDocumentSaveDto, user.get(), file);
            companyDocumentEntity = companyDocumentRepository.save(companyDocumentEntity);
            return companyDocumentConverter.convertCompanyDocumentEntityToCompanyDocumentDto(companyDocumentEntity);
        }
        log.error("Could not save database entry because user with id {} doesn't exist", companyDocumentSaveDto.getUserId());
        throw new EntityNotFoundException();
    }

    public List<CompanyDocumentGetDto> getCompanyDocuments() throws EntityNotFoundException {
        List<CompanyDocumentEntity> companyDocumentEntities = companyDocumentRepository.findAll();
        if (companyDocumentEntities.isEmpty()) {
            log.error("No company documents found in the database");
            return new ArrayList<>();
        }
        return companyDocumentConverter.convertCompanyDocumentEntitiesToCompanyDocumentGetDtos(companyDocumentEntities);
    }

    public CompanyDocumentDto getCompanyDocument(Long companyDocumentId) throws EntityNotFoundException {
        Optional<CompanyDocumentEntity> companyDocumentEntity = companyDocumentRepository.findById(companyDocumentId);
        if (companyDocumentEntity.isPresent()) {
            return companyDocumentConverter.convertCompanyDocumentEntityToCompanyDocumentDto(companyDocumentEntity.get());
        }
        log.error("Company document with id {} not found.", companyDocumentId);
        throw new EntityNotFoundException();
    }

    public void deleteCompanyDocument(Long companyDocumentId) throws EntityNotFoundException {
        Optional<CompanyDocumentEntity> companyDocumentEntity = companyDocumentRepository.findById(companyDocumentId);
        if (companyDocumentEntity.isEmpty()) {
            log.error("Company document with id {} not found.", companyDocumentId);
            throw new EntityNotFoundException();
        }
        companyDocumentRepository.deleteById(companyDocumentId);
    }

    public CompanyDocumentDto updateCompanyDocument(CompanyDocumentSaveDto companyDocumentDto) throws EntityNotFoundException {
        Optional<CompanyDocumentEntity> companyDocumentEntity = companyDocumentRepository.findById(companyDocumentDto.getId());
        if (companyDocumentEntity.isEmpty()) {
            log.error("Company document with id {} not found.", companyDocumentDto.getId());
            throw new EntityNotFoundException();
        }
        FileEntity oldFile = companyDocumentEntity.get().getFile();
        FileDto newFile = companyDocumentDto.getFile();
        if (newFile == null) {
            CompanyDocumentEntity updatedCompanyDocumentEntity = companyDocumentConverter.convertSaveCompanyDocumentDtoToCompanyDocumentEntity(companyDocumentDto, companyDocumentEntity.get().getUser(), null);
            updatedCompanyDocumentEntity = companyDocumentRepository.save(updatedCompanyDocumentEntity);
            if (oldFile != null) {
                fileRepository.deleteById(oldFile.getId());
            }
            return companyDocumentConverter.convertCompanyDocumentEntityToCompanyDocumentDto(updatedCompanyDocumentEntity);
        }
        FileEntity fileEntity = updateFile(companyDocumentDto.getFile());
        CompanyDocumentEntity updatedCompanyDocumentEntity = companyDocumentConverter.convertSaveCompanyDocumentDtoToCompanyDocumentEntity(companyDocumentDto, companyDocumentEntity.get().getUser(), fileEntity);
        return companyDocumentConverter.convertCompanyDocumentEntityToCompanyDocumentDto(companyDocumentRepository.save(updatedCompanyDocumentEntity));
    }
}
