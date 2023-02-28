package com.servidor.Practica4.Repos;

import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepo extends JpaRepository<Topic, Long> {
    List<Topic> findByCategoryEquals(Category category);
}
