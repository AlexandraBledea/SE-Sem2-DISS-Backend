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
@Table(name="training_document", indexes = {
        @Index(columnList = "required_level"),
        @Index(columnList = "reward")
})
@PrimaryKeyJoinColumn(name="id")
public class TrainingDocumentEntity extends DocumentEntity{

    @Column(name="required_level")
    private Integer requiredLevel;

    @Column
    private Integer reward;

}
