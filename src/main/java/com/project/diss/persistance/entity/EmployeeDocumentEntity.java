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
public class EmployeeDocumentEntity extends AbstractEntity{

    @OneToOne
    @MapsId
    @JoinColumn(name="fk_document_id")
    private DocumentEntity document;

    @Column
    private Boolean visibility;
}
