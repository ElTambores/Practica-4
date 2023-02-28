package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.TopicBuilder;
import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Repos.CategoryRepo;
import com.servidor.Practica4.Repos.TopicRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Category category = getCategoryFromSlug(slug);
        return topicRepo.findByCategoryEquals(category);
    }

    public Topic createTopic(TopicForm topicForm, Object userInfo) {
        User user = extractUserFromUserInfo(userInfo);
        Category topicCategory = getCategoryFromSlug(topicForm.getCategory());

        Topic topic = topicRepo.save(topicBuilder.fromForm(topicForm, topicCategory, user));

        return topic;
    }

    private User extractUserFromUserInfo(Object userInfo) {
        Map<String, Object> userMap = (Map<String, Object>) userInfo;
        UserBuilder userBuilder = new UserBuilder();
        return userBuilder.fromUserInfo(userMap);
    }

    private Category getCategoryFromSlug(String slug) {
        List<Category> categories = categoryRepo.findBySlugEquals(slug);
        if (categories.size() == 0) return null;
        return categories.get(0);
    }

    public Topic getTopic(Long topicId, Object userInfo) {
        User user = extractUserFromUserInfo(userInfo);
        Optional<Topic> topic = topicRepo.findById(topicId);
        return topic.orElse(null);
    }
}
