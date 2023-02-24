package com.servidor.Practica4.Repos;

import com.servidor.Practica4.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
