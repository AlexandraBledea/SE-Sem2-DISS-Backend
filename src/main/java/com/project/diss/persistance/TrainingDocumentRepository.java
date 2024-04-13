package com.project.diss.persistance;

import com.project.diss.persistance.entity.TrainingDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingDocumentRepository extends JpaRepository<TrainingDocumentEntity, Long> {
}
