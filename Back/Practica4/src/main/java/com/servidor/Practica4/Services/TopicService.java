package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.TopicBuilder;
import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Reply;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Repos.CategoryRepo;
import com.servidor.Practica4.Repos.ReplyRepo;
import com.servidor.Practica4.Repos.TopicRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TopicService {
    TopicRepo topicRepo;
    CategoryRepo categoryRepo;
    ReplyRepo replyRepo;

    TopicBuilder topicBuilder = new TopicBuilder();
    UserBuilder userBuilder = new UserBuilder();

    public TopicService(TopicRepo topicRepo, CategoryRepo categoryRepo, ReplyRepo replyRepo) {
        this.topicRepo = topicRepo;
        this.categoryRepo = categoryRepo;
        this.replyRepo = replyRepo;
    }

    public List<Map<String, Object>> getAllTopics(String slug) {
        Category category = getCategoryFromSlug(slug);
        List<Topic> topics = topicRepo.findByCategoryEquals(category);
        return topicBuilder.jsonFromList(topics);
    }

    public Topic createTopic(TopicForm topicForm, Object userInfo) {
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        Category topicCategory = getCategoryFromSlug(topicForm.getCategory());
        return topicRepo.save(topicBuilder.fromForm(topicForm, topicCategory, user));
    }

    private Category getCategoryFromSlug(String slug) {
        List<Category> categories = categoryRepo.findBySlugEquals(slug);
        if (categories.size() == 0) return null;
        return categories.get(0);
    }

    public Map<String, Object> getTopic(Long topicId) {
        Topic topic = getTopicById(topicId);
        List<Reply> replies = replyRepo.findByTopicEquals(topic);
        return topicBuilder.createJson(topic, replies);
    }

    private Topic getTopicById(long topicId) {
        Optional<Topic> optional = topicRepo.findById(topicId);
        return optional.orElse(null);
    }
}
