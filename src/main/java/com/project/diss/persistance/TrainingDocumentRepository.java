package com.project.diss.persistance;

import com.project.diss.persistance.entity.TrainingDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingDocumentRepository extends JpaRepository<TrainingDocumentEntity, Long> {

    @Query("SELECT td FROM TrainingDocumentEntity td JOIN BadgeEntity b ON td.id = b.document.id WHERE b.user.id =:id AND b.progressStatus = 'Completed' AND b.document.id IS NOT NULL")
    List<TrainingDocumentEntity> findCompletedTrainingDocumentsForUser(Long id);

    @Query("SELECT td FROM TrainingDocumentEntity td WHERE td.id NOT IN (" +
            "SELECT b.document.id FROM BadgeEntity b WHERE b.user.id = :id AND b.progressStatus = 'Completed' AND b.document.id IS NOT NULL) " +
            "ORDER BY td.requiredLevel ASC, td.reward DESC")
    List<TrainingDocumentEntity> findTodoTrainingDocumentsForUser(Long id);
}
