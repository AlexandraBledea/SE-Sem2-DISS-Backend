package com.project.diss.converters;

import com.project.diss.dto.SaveTrainingDocumentDto;
import com.project.diss.dto.TrainingDocumentDto;
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

    @Autowired
    public TrainingDocumentConverter(UserConverter userConverter, FileConverter fileConverter) {
        this.userConverter = userConverter;
        this.fileConverter = fileConverter;
    }

    public TrainingDocumentEntity convertSaveTrainingDocumentDtoToTrainingDocumentEntity(SaveTrainingDocumentDto dto, UserEntity user, FileEntity file) {
        if (dto == null) {
            return null;
        }

        TrainingDocumentEntity trainingDocument = new TrainingDocumentEntity();

        trainingDocument.setTitle(dto.getTitle());
        trainingDocument.setText(dto.getText());
        trainingDocument.setKeywords(dto.getKeywords());
        trainingDocument.setPointsRequired(dto.getPointsRequired());
        trainingDocument.setReward(dto.getReward());
        trainingDocument.setTotalPages(dto.getTotalPages());
        trainingDocument.setUser(user);
        trainingDocument.setFile(file);
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
        trainingDocument.setPointsRequired(entity.getPointsRequired());
        trainingDocument.setReward(entity.getReward());
        trainingDocument.setTotalPages(entity.getTotalPages());
        trainingDocument.setUser(this.userConverter.convertUserEntityToUserDto(entity.getUser()));
        trainingDocument.setFile(this.fileConverter.convertFileEntityToFileDto(entity.getFile()));
        return trainingDocument;
    }

    public TrainingDocumentEntity convertTrainingDocumentDtoToTrainingDocumentEntity(TrainingDocumentDto dto, UserEntity user) {
        if (dto == null) {
            return null;
        }

        TrainingDocumentEntity trainingDocument = new TrainingDocumentEntity();

        trainingDocument.setId(dto.getId());
        trainingDocument.setTitle(dto.getTitle());
        trainingDocument.setText(dto.getText());
        trainingDocument.setKeywords(dto.getKeywords());
        trainingDocument.setLastModified(dto.getLastModified());
        trainingDocument.setPointsRequired(dto.getPointsRequired());
        trainingDocument.setTotalPages(dto.getTotalPages());
        trainingDocument.setReward(dto.getReward());
        trainingDocument.setUser(user);
        trainingDocument.setFile(this.fileConverter.convertFileDtoToFileEntity(dto.getFile()));
        return trainingDocument;
    }

    public List<TrainingDocumentDto> convertTrainingDocumentEntitiesToTrainingDocumentDtos(List<TrainingDocumentEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<TrainingDocumentDto> list = new ArrayList<>(entities.size());
        for (TrainingDocumentEntity trainingDocument : entities) {
            list.add(convertTrainingDocumentEntityToTrainingDocumentDto(trainingDocument));
        }

        return list;
    }

}
