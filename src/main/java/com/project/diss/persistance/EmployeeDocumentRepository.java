package com.project.diss.persistance;

import com.project.diss.persistance.entity.EmployeeDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocumentEntity, Long>{
}
