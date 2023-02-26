package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.TopicBuilder;
import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Repos.CategoryRepo;
import com.servidor.Practica4.Repos.TopicRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    TopicRepo topicRepo;
    CategoryRepo categoryRepo;
    TopicBuilder topicBuilder = new TopicBuilder();

    public TopicService(TopicRepo topicRepo, CategoryRepo categoryRepo) {
        this.topicRepo = topicRepo;
        this.categoryRepo = categoryRepo;
    }

    public List<Topic> getAllTopics(String slug) {
        List<Category> categories = categoryRepo.findBySlugEquals(slug);
        if (categories.size() == 0) return null;
        long categoryId = categories.get(0).get_id();
        return topicRepo.findByIdEquals(categoryId);
    }

    public Topic createTopic(TopicForm topicForm) {
        return topicRepo.save(topicBuilder.fromForm(topicForm));
    }
}
