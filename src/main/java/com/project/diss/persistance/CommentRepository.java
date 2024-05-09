package com.project.diss.persistance;

import com.project.diss.persistance.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c WHERE c.document.id = :documentId " +
            "ORDER BY c.created DESC")
    List<CommentEntity> findByDocumentId(Long documentId);
}
