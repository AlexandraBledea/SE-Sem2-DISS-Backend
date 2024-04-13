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
@Table(name="badge")
public class BadgeEntity extends AbstractEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_document_id")
    private DocumentEntity document;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_user_id")
    private UserEntity user;
}
