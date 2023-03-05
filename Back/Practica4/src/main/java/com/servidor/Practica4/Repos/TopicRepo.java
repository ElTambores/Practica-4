package com.servidor.Practica4.Repos;

import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Topic;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TopicRepo extends JpaRepository<Topic, Long> {
    List<Topic> findByCategoryEquals(Category category);

    @Modifying
    @Transactional
    @Query("UPDATE Topic t SET t.category=:category, t.content=:content, t.title=:title, t.updatedAt=:updateDate WHERE t._id=:topicId")
    void update(long topicId, Category category, String content, String title, Date updateDate);

    @Modifying
    @Transactional
    @Query("UPDATE Topic t SET t.views=:views WHERE t._id=:topicId")
    void updateViews(long topicId, int views);
}
