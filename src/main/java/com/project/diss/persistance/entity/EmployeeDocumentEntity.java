package com.project.diss.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee_document")
@PrimaryKeyJoinColumn(name="id")
public class EmployeeDocumentEntity extends DocumentEntity {

    @Column
    private Boolean visibility;
}
