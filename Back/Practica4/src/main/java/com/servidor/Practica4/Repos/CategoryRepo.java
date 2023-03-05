package com.servidor.Practica4.Repos;

import com.servidor.Practica4.Models.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findBySlugEquals(String slut);

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.title=:newTitle, c.description=:newDescription WHERE c.slug=:slug")
    void update(String slug, String newTitle, String newDescription);

    long deleteBySlug(String slug);
}


