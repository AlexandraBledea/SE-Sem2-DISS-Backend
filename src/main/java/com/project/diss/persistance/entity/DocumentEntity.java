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
@Table(name = "document")
@Inheritance(strategy = InheritanceType.JOINED)
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(name="file")
    private byte[] document;

    @Column
    private String keywords;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime created;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    //TODO modify the cascade type, because deleting a document => deleting the user and that's not what we want
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    private List<CommentEntity> comments;

}
