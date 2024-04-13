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
@Table(name="company_document")
public class CompanyDocumentEntity extends AbstractEntity{

    @OneToOne
    @MapsId
    @JoinColumn(name="fk_document_id")
    private DocumentEntity document;
}
