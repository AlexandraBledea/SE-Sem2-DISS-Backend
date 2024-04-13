package com.project.diss.persistance;

import com.project.diss.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailAndPassword(String email, String password);

    UserEntity findByEmail(String email);
}
