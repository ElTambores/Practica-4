package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.TopicBuilder;
import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Exceptions.ErrorUpdatingException;
import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Reply;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Repos.CategoryRepo;
import com.servidor.Practica4.Repos.ReplyRepo;
import com.servidor.Practica4.Repos.TopicRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
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
        getNumberReplies(topics);
        return topicBuilder.jsonFromList(topics);
    }

    private void getNumberReplies(List<Topic> topics) {
        for (Topic topic : topics) {
            List<Reply> replies = replyRepo.findByTopicEquals(topic);
            topic.setReplies(replies.size());
        }
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
        topic.setViews(topic.getViews() + 1);
        topicRepo.updateViews(topicId, topic.getViews());
        List<Reply> replies = replyRepo.findByTopicEquals(topic);
        return topicBuilder.createJson(topic, replies);
    }

    private Topic getTopicById(long topicId) {
        Optional<Topic> optional = topicRepo.findById(topicId);
        return optional.orElse(null);
    }

    public Map<String, Object> updateTopic(TopicForm topicForm, long topicId, Object userInfo) {
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        Topic topic = topicRepo.findById(topicId).orElseGet(null);
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (user.getRole().equals("admin") || user.getId().equals(topic.getUser().getId())) {
            try {
                Category category = getCategoryFromSlug(topicForm.getCategory());
                Date updateDate = new Date(System.currentTimeMillis());
                topicRepo.update(topicId, category, topicForm.getContent(), topicForm.getTitle(), updateDate);
                Topic newTopic = topicRepo.findById(topicId).orElseGet(null);
                if (newTopic == null) throw new ErrorUpdatingException();
                List<Reply> replies = replyRepo.findByTopicEquals(topic);
                return topicBuilder.createJson(newTopic, replies);
            } catch (Exception e) {
                throw new ErrorUpdatingException();
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public Boolean deleteTopic(long topicId, Object userInfo) {
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        Topic topic = topicRepo.findById(topicId).orElseGet(null);
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (user.getRole().equals("admin") || user.getId().equals(topic.getUser().getId())) {
            try {
                topicRepo.deleteById(topicId);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
