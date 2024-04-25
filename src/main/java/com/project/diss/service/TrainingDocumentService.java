package com.project.diss.service;

import com.project.diss.converters.FileConverter;
import com.project.diss.dto.FileDto;
import com.project.diss.dto.SaveTrainingDocumentDto;
import com.project.diss.dto.TrainingDocumentDto;
import com.project.diss.converters.TrainingDocumentConverter;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.persistance.DocumentRepository;
import com.project.diss.persistance.FileRepository;
import com.project.diss.persistance.TrainingDocumentRepository;
import com.project.diss.persistance.UserRepository;
import com.project.diss.persistance.entity.FileEntity;
import com.project.diss.persistance.entity.TrainingDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainingDocumentService {

    UserRepository userRepository;
    DocumentRepository documentRepository;
    FileRepository fileRepository;

    TrainingDocumentRepository trainingDocumentRepository;

    TrainingDocumentConverter documentConverter;

    FileConverter fileConverter;

    @Autowired
    public TrainingDocumentService(UserRepository userRepository, DocumentRepository documentRepository, TrainingDocumentRepository trainingDocumentRepository, TrainingDocumentConverter documentConverter) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.documentConverter = documentConverter;
        this.trainingDocumentRepository = trainingDocumentRepository;
    }

    public FileEntity createFile(FileDto fileDto) {
        //TODO throw an error if the fileDto is null
        if (fileDto == null) {
            return null;
        } else {
            FileEntity fileEntity = fileConverter.convertFileDtoToFileEntity(fileDto);
            return fileRepository.save(fileEntity);
        }
    }

    public TrainingDocumentDto createTrainingDocument(SaveTrainingDocumentDto trainingDocument) throws EntityNotFoundException {
        log.info("Creating training document: {}", trainingDocument);
        Optional<UserEntity> user = userRepository.findById(trainingDocument.getUserId());
        FileEntity file = createFile(trainingDocument.getFile());
        if (user.isPresent()) {
            TrainingDocumentEntity trainingDocumentEntity = documentConverter.convertSaveTrainingDocumentDtoToTrainingDocumentEntity(trainingDocument, user.get(), file);
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

}
