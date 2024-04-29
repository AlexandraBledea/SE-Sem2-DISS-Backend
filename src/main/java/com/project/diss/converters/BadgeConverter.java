package com.project.diss.converters;

import com.project.diss.dto.BadgeDto;
import com.project.diss.persistance.entity.BadgeEntity;
import com.project.diss.persistance.entity.TrainingDocumentEntity;
import com.project.diss.persistance.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class BadgeConverter {

    public BadgeDto convertBadgeEntityToBadgeDto(BadgeEntity entity) {
        if (entity == null) {
            return null;
        }

        BadgeDto badge = new BadgeDto();

        badge.setTrainingId(entity.getDocument().getId());
        badge.setUserId(entity.getUser().getId());
        badge.setProgressStatus(entity.getProgressStatus());
        badge.setCurrentPage(entity.getCurrentPage());
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
        return badge;
    }
}
