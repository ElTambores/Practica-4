package com.servidor.Practica4.Repos;

import com.servidor.Practica4.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByEmailEquals(String name);
}
