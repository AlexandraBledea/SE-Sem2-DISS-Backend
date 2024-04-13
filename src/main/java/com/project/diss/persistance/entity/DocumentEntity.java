package com.project.diss.persistance.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column
    private byte[] document;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime created;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    private List<CommentEntity> comments;

}
