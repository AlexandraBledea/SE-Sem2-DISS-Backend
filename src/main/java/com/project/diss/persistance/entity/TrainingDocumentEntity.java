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
@Table(name="training_document")
@PrimaryKeyJoinColumn(name="id")
public class TrainingDocumentEntity extends DocumentEntity{

    @Column(name="required_points")
    private Integer pointsRequired;

    @Column
    private Integer reward;

    @Column(name="total_pages")
    private Integer totalPages;
}
