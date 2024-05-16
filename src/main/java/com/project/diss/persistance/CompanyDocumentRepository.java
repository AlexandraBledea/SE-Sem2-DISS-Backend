package com.project.diss.persistance;

import com.project.diss.persistance.entity.CompanyDocumentEntity;
import com.project.diss.persistance.entity.TrainingDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface CompanyDocumentRepository extends JpaRepository<CompanyDocumentEntity, Long>{
    @Query("SELECT d, " +
            "CASE " +
            "WHEN d.keywords LIKE LOWER(CONCAT('%', :search, '%')) THEN 1 " +
            "WHEN d.title LIKE LOWER(CONCAT('%', :search, '%')) THEN 2 " +
            "WHEN d.text LIKE LOWER(CONCAT('%', :search, '%')) THEN 3 " +
            "ELSE 4 " +
            "END AS relevance " +
            "FROM CompanyDocumentEntity d " +
            "WHERE d.keywords LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR d.title LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR d.text LIKE LOWER(CONCAT('%', :search, '%')) " +
            "ORDER BY relevance")
    List<CompanyDocumentEntity> searchForCompanyDocuments(String search);
}
