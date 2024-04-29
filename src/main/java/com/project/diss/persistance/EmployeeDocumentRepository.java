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

}
