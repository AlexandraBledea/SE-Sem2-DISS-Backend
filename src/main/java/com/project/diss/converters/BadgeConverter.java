package com.project.diss.converters;

import com.project.diss.dto.BadgeDto;
import com.project.diss.dto.EmployeeDocumentGetDto;
import com.project.diss.persistance.entity.BadgeEntity;
import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import com.project.diss.persistance.entity.TrainingDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BadgeConverter {

    public BadgeDto convertBadgeEntityToBadgeDto(BadgeEntity entity) {
        if (entity == null) {
            return null;
        }

        BadgeDto badge = new BadgeDto();

        badge.setUserId(entity.getUser().getId());
        badge.setProgressStatus(entity.getProgressStatus());
        badge.setCurrentPage(entity.getCurrentPage());
        badge.setName(entity.getName());
        return badge;
    }

    public BadgeEntity convertBadgeDtoToBadgeEntity(BadgeDto dto, UserEntity userEntity, TrainingDocumentEntity trainingDocumentEntity) {
        if (dto == null) {
            return null;
        }

        BadgeEntity badge = new BadgeEntity();

        badge.setDocument(trainingDocumentEntity);
        badge.setUser(userEntity);
        badge.setProgressStatus(dto.getProgressStatus());
        badge.setCurrentPage(dto.getCurrentPage());
        badge.setName(trainingDocumentEntity.getTitle());
        return badge;
    }

    public List<BadgeDto> convertBadgeEntitiesToBadgeDtos(List<BadgeEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<BadgeDto> list = new ArrayList<>(entities.size());
        for (BadgeEntity employeeDocument : entities) {
            list.add(convertBadgeEntityToBadgeDto(employeeDocument));
        }

        return list;
    }
}
