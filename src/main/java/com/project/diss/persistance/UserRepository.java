package com.project.diss.persistance;

import com.project.diss.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailAndPassword(String email, String password);

    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.firstname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.lastname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.role) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.department) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.location) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.type) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<UserEntity> searchForUser(@Param("search") String search);
}
