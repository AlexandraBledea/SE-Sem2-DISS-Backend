package com.project.diss.converters;

import com.project.diss.controller.dto.EmployeeDocumentDto;
import com.project.diss.controller.dto.TrainingDocumentDto;
import com.project.diss.controller.dto.UserDto;
import com.project.diss.persistance.entity.DocumentEntity;
import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import com.project.diss.persistance.entity.TrainingDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentConverter {

    public TrainingDocumentDto convertTrainingDocumentEntityToTrainingDocumentDto(TrainingDocumentEntity entity) {
        if (entity == null) {
            return null;
        }

        TrainingDocumentDto trainingDocument = new TrainingDocumentDto();

        trainingDocument.setId(entity.getId());
        trainingDocument.setTitle(entity.getTitle());
        trainingDocument.setText(entity.getText());
        trainingDocument.setDocument(entity.getDocument());
        trainingDocument.setKeywords(entity.getKeywords());
        trainingDocument.setCreated(entity.getCreated());
        trainingDocument.setLastModified(entity.getLastModified());
        trainingDocument.setPointsRequired(entity.getPointsRequired());
        trainingDocument.setReward(entity.getReward());
        trainingDocument.setTotalPages(entity.getTotalPages());
        trainingDocument.setUserId(entity.getUser().getId());
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
        trainingDocument.setDocument(dto.getDocument());
        trainingDocument.setKeywords(dto.getKeywords());
        trainingDocument.setCreated(dto.getCreated());
        trainingDocument.setLastModified(dto.getLastModified());
        trainingDocument.setPointsRequired(dto.getPointsRequired());
        trainingDocument.setTotalPages(dto.getTotalPages());
        trainingDocument.setReward(dto.getReward());
        trainingDocument.setUser(user);
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

    public EmployeeDocumentDto convertEmployeeDocumentEntityToEmployeeDocumentDto(EmployeeDocumentEntity entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDocumentDto employeeDocument = new EmployeeDocumentDto();

        employeeDocument.setId(entity.getId());
        employeeDocument.setTitle(entity.getTitle());
        employeeDocument.setText(entity.getText());
        employeeDocument.setDocument(entity.getDocument());
        employeeDocument.setKeywords(entity.getKeywords());
        employeeDocument.setCreated(entity.getCreated());
        employeeDocument.setLastModified(entity.getLastModified());
        employeeDocument.setVisibility(entity.getVisibility());
        employeeDocument.setUserId(entity.getUser().getId());
        return employeeDocument;
    }

    public EmployeeDocumentEntity convertEmployeeDocumentDtoToEmployeeDocumentEntity(EmployeeDocumentDto dto, UserEntity user) {
        if (dto == null) {
            return null;
        }

        EmployeeDocumentEntity employeeDocument = new EmployeeDocumentEntity();

        employeeDocument.setId(dto.getId());
        employeeDocument.setTitle(dto.getTitle());
        employeeDocument.setText(dto.getText());
        employeeDocument.setDocument(dto.getDocument());
        employeeDocument.setKeywords(dto.getKeywords());
        employeeDocument.setCreated(dto.getCreated());
        employeeDocument.setLastModified(dto.getLastModified());
        employeeDocument.setVisibility(dto.isVisibility());
        employeeDocument.setUser(user);
        return employeeDocument;
    }

    public List<EmployeeDocumentDto> convertEmployeeDocumentEntitiesToEmployeeDocumentDtos(List<EmployeeDocumentEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<EmployeeDocumentDto> list = new ArrayList<>(entities.size());
        for (EmployeeDocumentEntity employeeDocument : entities) {
            list.add(convertEmployeeDocumentEntityToEmployeeDocumentDto(employeeDocument));
        }

        return list;
    }

}
