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
public class BadgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_document_id")
    private DocumentEntity document;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_user_id")
    private UserEntity user;

    @Column(name="progress_status")
    private String progressStatus;

    @Column(name="current_page")
    private Integer currentPage;
}
