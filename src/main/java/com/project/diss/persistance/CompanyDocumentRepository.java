package com.project.diss.persistance;

import com.project.diss.persistance.entity.CompanyDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDocumentRepository extends JpaRepository<CompanyDocumentEntity, Long>{
}
