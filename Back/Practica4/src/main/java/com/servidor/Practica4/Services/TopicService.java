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
        assert category != null;
        long categoryId = category.get_id();
        return topicRepo.findByIdEquals(categoryId);
    }

    public Topic createTopic(TopicForm topicForm, Object userInfo) {
        User user = extractUserFromUserInfo(userInfo);
        Category topicCategory = getCategoryFromSlug(topicForm.getCategory());

        return topicRepo.save(topicBuilder.fromForm(topicForm, topicCategory, user));
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

    public List<Topic> getTopicReplies(int topicId) {
        return null;
    }
}
