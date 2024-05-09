package com.project.diss.service;

import com.project.diss.converters.BadgeConverter;
import com.project.diss.converters.FileConverter;
import com.project.diss.dto.*;
import com.project.diss.converters.TrainingDocumentConverter;
import com.project.diss.exception.EntityNotFoundException;
import com.project.diss.persistance.*;
import com.project.diss.persistance.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class TrainingDocumentService {

    UserRepository userRepository;
    DocumentRepository documentRepository;
    FileRepository fileRepository;
    TrainingDocumentRepository trainingDocumentRepository;
    BadgeRepository badgeRepository;
    TrainingDocumentConverter documentConverter;
    FileConverter fileConverter;
    BadgeConverter badgeConverter;

    @Autowired
    public TrainingDocumentService(UserRepository userRepository, DocumentRepository documentRepository,
                                   TrainingDocumentRepository trainingDocumentRepository, FileConverter fileConverter,
                                   TrainingDocumentConverter documentConverter, FileRepository fileRepository,
                                   BadgeConverter badgeConverter, BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.documentConverter = documentConverter;
        this.trainingDocumentRepository = trainingDocumentRepository;
        this.fileConverter = fileConverter;
        this.fileRepository = fileRepository;
        this.badgeConverter = badgeConverter;
        this.badgeRepository = badgeRepository;
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

    public TrainingDocumentDto createTrainingDocument(TrainingDocumentSaveDto trainingDocument) throws EntityNotFoundException {
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


    public List<TrainingDocumentGetDto> getCompletedTrainingDocuments(Long id) {
        List<TrainingDocumentEntity> trainingDocumentEntities = trainingDocumentRepository.findCompletedTrainingDocumentsForUser(id);
        if (trainingDocumentEntities.isEmpty()) {
            return new ArrayList<>();
        }
        return documentConverter.convertTrainingDocumentEntitiesToTrainingDocumentGetDtos(trainingDocumentEntities);
    }

    public List<TrainingDocumentGetDto> getTodoTrainingDocuments(Long id) {
        List<TrainingDocumentEntity> todoTrainingDocuments = trainingDocumentRepository.findTodoTrainingDocumentsForUser(id);
        if (todoTrainingDocuments.isEmpty()) {
            return new ArrayList<>();
        }
        return documentConverter.convertTrainingDocumentEntitiesToTrainingDocumentGetDtos(todoTrainingDocuments);
    }

    public TrainingDocumentViewDto getTrainingDocument(TrainingDocumentStartDto dto) throws EntityNotFoundException {
        Optional<TrainingDocumentEntity> trainingDocument = trainingDocumentRepository.findById(dto.getTrainingId());
        if (trainingDocument.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Optional<BadgeEntity> badgeEntity = badgeRepository.findByUserIdAndDocumentId(dto.getUserId(), dto.getTrainingId());
        if (badgeEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return documentConverter.convertTrainingDocumentEntityToTrainingDocumentViewDto(trainingDocument.get(), badgeEntity.get());
    }

    public void updateBadgeTraining(BadgeDto badgeDto) throws EntityNotFoundException {
        Optional<BadgeEntity> badgeEntity = badgeRepository.findByUserIdAndDocumentId(badgeDto.getUserId(), badgeDto.getTrainingId());
        if (badgeEntity.isEmpty()) {
           createBadgeTraining(badgeDto);
        } else {
            if(badgeDto.getProgressStatus() != null && badgeDto.getCurrentPage() != null) {
                badgeEntity.get().setProgressStatus(badgeDto.getProgressStatus());
                badgeEntity.get().setCurrentPage(badgeDto.getCurrentPage());
                badgeRepository.save(badgeEntity.get());
            }
        }
    }

    public void createBadgeTraining(BadgeDto badgeDto) throws EntityNotFoundException {
        Optional<TrainingDocumentEntity> trainingDocument = trainingDocumentRepository.findById(badgeDto.getTrainingId());
        if (trainingDocument.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Optional<UserEntity> user = userRepository.findById(badgeDto.getUserId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BadgeEntity badge = this.badgeConverter.convertBadgeDtoToBadgeEntity(badgeDto, user.get(), trainingDocument.get());
        badge.setProgressStatus("In progress");
        badge.setCurrentPage(1);
        badgeRepository.save(badge);
    }

    public void updateUserProgress(UserProgressUpdateDto dto) throws EntityNotFoundException {
        Optional<UserEntity> user = userRepository.findById(dto.getId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }
        user.get().setPoints(dto.getPoints());
        user.get().setLevel(dto.getLevel());
        userRepository.save(user.get());
    }

    public void deleteTrainingDocument(Long id) throws EntityNotFoundException {
        List<BadgeEntity> badgeEntitiesCompleted = badgeRepository.findByDocumentIdAndCompleted(id);
        badgeEntitiesCompleted = badgeEntitiesCompleted.stream()
                .peek(badge -> badge.setDocument(null))
                .toList();
        badgeRepository.saveAll(badgeEntitiesCompleted);

        List<BadgeEntity> badgeEntitiesInProgress = badgeRepository.findByDocumentIdAndInProgress(id);
        badgeRepository.deleteAll(badgeEntitiesInProgress);

        Optional<TrainingDocumentEntity> trainingDocumentEntity = trainingDocumentRepository.findById(id);
        if (trainingDocumentEntity.isEmpty()) {
            log.info("Could not find training document with id {}", id);
            throw new EntityNotFoundException();
        }
        trainingDocumentRepository.deleteById(id);
    }

    public List<TrainingDocumentGetDto> searchForTrainingDocuments(String searchKey) {
        List<TrainingDocumentEntity> trainingDocumentEntityList = trainingDocumentRepository.searchForTrainingDocuments(searchKey);
        if (trainingDocumentEntityList.isEmpty()) {
            return new ArrayList<>();
        }
        return documentConverter.convertTrainingDocumentEntitiesToTrainingDocumentGetDtos(trainingDocumentEntityList);
    }
}
