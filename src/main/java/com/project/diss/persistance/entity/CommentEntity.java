package com.project.diss.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comment")
public class CommentEntity extends AbstractEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_document_id")
    private DocumentEntity document;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_user_id")
    private UserEntity user;

    @Column
    private String text;

    @CreationTimestamp
    @Column(name="date")
    private LocalDateTime created;
}
