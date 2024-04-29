package com.project.diss.converters;

import com.project.diss.dto.TrainingDocumentGetDto;
import com.project.diss.dto.TrainingDocumentSaveDto;
import com.project.diss.dto.TrainingDocumentDto;
import com.project.diss.dto.TrainingDocumentViewDto;
import com.project.diss.persistance.entity.BadgeEntity;
import com.project.diss.persistance.entity.FileEntity;
import com.project.diss.persistance.entity.TrainingDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingDocumentConverter {

    UserConverter userConverter;
    FileConverter fileConverter;

    BadgeConverter badgeConverter;

    @Autowired
    public TrainingDocumentConverter(UserConverter userConverter, FileConverter fileConverter,
                                     BadgeConverter badgeConverter) {
        this.userConverter = userConverter;
        this.fileConverter = fileConverter;
        this.badgeConverter = badgeConverter;
    }

    public TrainingDocumentEntity convertSaveTrainingDocumentDtoToTrainingDocumentEntity(TrainingDocumentSaveDto dto, UserEntity user, FileEntity file) {
        if (dto == null) {
            return null;
        }

        TrainingDocumentEntity trainingDocument = new TrainingDocumentEntity();

        trainingDocument.setId(dto.getId());
        trainingDocument.setTitle(dto.getTitle());
        trainingDocument.setText(dto.getText());
        trainingDocument.setKeywords(dto.getKeywords());
        trainingDocument.setRequiredLevel(dto.getRequiredLevel());
        trainingDocument.setReward(dto.getReward());
        trainingDocument.setUser(user);
        trainingDocument.setFile(file);
        return trainingDocument;
    }

    public TrainingDocumentViewDto convertTrainingDocumentEntityToTrainingDocumentViewDto(TrainingDocumentEntity entity, BadgeEntity badge) {
        if (entity == null) {
            return null;
        }

        TrainingDocumentViewDto trainingDocument = new TrainingDocumentViewDto();

        trainingDocument.setId(entity.getId());
        trainingDocument.setTitle(entity.getTitle());
        trainingDocument.setReward(entity.getReward());
        trainingDocument.setUser(this.userConverter.convertUserEntityToUserDocumentDto(entity.getUser()));
        trainingDocument.setFile(this.fileConverter.convertFileEntityToFileDto(entity.getFile()));
        trainingDocument.setBadge(this.badgeConverter.convertBadgeEntityToBadgeDto(badge));
        return trainingDocument;
    }

    public TrainingDocumentDto convertTrainingDocumentEntityToTrainingDocumentDto(TrainingDocumentEntity entity) {
        if (entity == null) {
            return null;
        }

        TrainingDocumentDto trainingDocument = new TrainingDocumentDto();

        trainingDocument.setId(entity.getId());
        trainingDocument.setTitle(entity.getTitle());
        trainingDocument.setText(entity.getText());
        trainingDocument.setKeywords(entity.getKeywords());
        trainingDocument.setLastModified(entity.getLastModified());
        trainingDocument.setRequiredLevel(entity.getRequiredLevel());
        trainingDocument.setReward(entity.getReward());
        trainingDocument.setUser(this.userConverter.convertUserEntityToUserDocumentDto(entity.getUser()));
        trainingDocument.setFile(this.fileConverter.convertFileEntityToFileDto(entity.getFile()));
        return trainingDocument;
    }

    public TrainingDocumentGetDto convertTrainingDocumentEntityToTrainingDocumentGetDto(TrainingDocumentEntity entity) {
        if (entity == null) {
            return null;
        }

        TrainingDocumentGetDto trainingDocument = new TrainingDocumentGetDto();

        trainingDocument.setId(entity.getId());
        trainingDocument.setTitle(entity.getTitle());
        trainingDocument.setRequiredLevel(entity.getRequiredLevel());
        trainingDocument.setReward(entity.getReward());
        trainingDocument.setText(entity.getText());
        trainingDocument.setUser(this.userConverter.convertUserEntityToUserDocumentDto(entity.getUser()));
        return trainingDocument;
    }

    public List<TrainingDocumentGetDto> convertTrainingDocumentEntitiesToTrainingDocumentGetDtos(List<TrainingDocumentEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<TrainingDocumentGetDto> list = new ArrayList<>(entities.size());
        for (TrainingDocumentEntity trainingDocument : entities) {
            list.add(convertTrainingDocumentEntityToTrainingDocumentGetDto(trainingDocument));
        }

        return list;
    }

}
