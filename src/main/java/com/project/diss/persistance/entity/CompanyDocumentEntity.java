package com.project.diss.persistance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="company_document")
@PrimaryKeyJoinColumn(name="id")
public class CompanyDocumentEntity extends DocumentEntity {

}
