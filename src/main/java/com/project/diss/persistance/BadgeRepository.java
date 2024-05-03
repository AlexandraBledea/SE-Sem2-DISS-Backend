package com.project.diss.persistance;

import com.project.diss.persistance.entity.BadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<BadgeEntity, Long> {

    @Query("SELECT b FROM BadgeEntity b WHERE b.user.id = :userId AND b.document.id = :documentId")
    Optional<BadgeEntity> findByUserIdAndDocumentId(Long userId, Long documentId);

    @Query("SELECT b FROM BadgeEntity b WHERE b.document.id = :documentId AND b.progressStatus = 'In progress'")
    List<BadgeEntity> findByDocumentIdAndInProgress(Long documentId);

    @Query("SELECT b FROM BadgeEntity b WHERE b.document.id = :documentId AND b.progressStatus = 'Completed'")
    List<BadgeEntity> findByDocumentIdAndCompleted(Long documentId);

    @Query("SELECT b FROM BadgeEntity b WHERE b.user.id = :userId AND b.progressStatus = 'Completed'")
    List<BadgeEntity> findByUserIdAndCompleted(Long userId);
}
