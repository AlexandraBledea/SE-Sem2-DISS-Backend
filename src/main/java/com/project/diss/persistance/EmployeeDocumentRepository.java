package com.project.diss.persistance;

import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocumentEntity, Long>{

    @Query("SELECT d FROM EmployeeDocumentEntity d WHERE d.user.id = :id")
    List<EmployeeDocumentEntity> findDocumentsOwnedBy(Long id);

    @Query("SELECT d FROM EmployeeDocumentEntity d WHERE d.user.id = :id OR " +
            "(d.visibility = true AND d.user.id != :id) ORDER BY d.lastModified DESC")
    List<EmployeeDocumentEntity> findAllRelevantDocumentsSorted(Long id);

    @Query("SELECT d, " +
            "CASE " +
            "WHEN d.keywords LIKE LOWER(CONCAT('%', :search, '%')) THEN 1 " +
            "WHEN d.title LIKE LOWER(CONCAT('%', :search, '%')) THEN 2 " +
            "WHEN d.text LIKE LOWER(CONCAT('%', :search, '%')) THEN 3 " +
            "ELSE 4 " +
            "END AS relevance " +
            "FROM EmployeeDocumentEntity d " +
            "WHERE d.keywords LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR d.title LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR d.text LIKE LOWER(CONCAT('%', :search, '%')) " +
            "ORDER BY relevance")
    List<EmployeeDocumentEntity> searchForEmployeeDocument(String search);

}
