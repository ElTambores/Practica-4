package com.servidor.Practica4.Repos;

import com.servidor.Practica4.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByEmailEquals(String name);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name=:newName, u.email=:newEmail WHERE u.id=:id")
    void updateUser(long id, String newName, String newEmail);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password=:newPassword WHERE u.id=:id")
    void updateUserPassword(long id, String newPassword);
}
