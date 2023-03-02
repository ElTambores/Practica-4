package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.ReplyBuilder;
import com.servidor.Practica4.Builders.TopicBuilder;
import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Forms.ReplyForm;
import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Reply;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Repos.CategoryRepo;
import com.servidor.Practica4.Repos.ReplyRepo;
import com.servidor.Practica4.Repos.TopicRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TopicService {
    TopicRepo topicRepo;
    CategoryRepo categoryRepo;
    ReplyRepo replyRepo;

    TopicBuilder topicBuilder = new TopicBuilder();
    ReplyBuilder replyBuilder = new ReplyBuilder();

    public TopicService(TopicRepo topicRepo, CategoryRepo categoryRepo, ReplyRepo replyRepo) {
        this.topicRepo = topicRepo;
        this.categoryRepo = categoryRepo;
        this.replyRepo = replyRepo;
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

    public Map<String, Object> getTopic(Long topicId, Object userInfo) {
        User user = extractUserFromUserInfo(userInfo);
        Topic topic = getTopicById(topicId);

        return topicBuilder.getJson(topic, user);
    }

    public Map<String, Object> postReply(ReplyForm replyForm, long topicId, Object userInfo) {
        Topic topic = getTopicById(topicId);
        Reply reply = replyRepo.save(replyBuilder.fromForm(replyForm, topic));

        return replyBuilder.getJson(reply, extractUserFromUserInfo(userInfo));

    }

    private Topic getTopicById(long topicId) {
        Optional<Topic> optional = topicRepo.findById(topicId);
        return optional.orElse(null);
    }
}
