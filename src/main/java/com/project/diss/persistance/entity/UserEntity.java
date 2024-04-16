package com.project.diss.persistance.entity;


import com.project.diss.persistance.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class UserEntity extends AbstractEntity{

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column
    private String role;

    @Column
    private String department;

    @Column
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType type;

    @Column
    private Integer level;

    @Column
    private Integer points;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<DocumentEntity> documents;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<BadgeEntity> badges;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<CommentEntity> comments;

}
