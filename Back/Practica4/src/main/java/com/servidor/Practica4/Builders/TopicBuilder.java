package com.servidor.Practica4.Builders;

import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Reply;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;

import java.util.*;

public class TopicBuilder {
    UserBuilder userBuilder = new UserBuilder();

    public Topic fromForm(TopicForm topicForm, Category topicCategory, User user) {
        Topic topic = new Topic();
        Date creationDate = new Date(System.currentTimeMillis());

        topic.setTitle(topicForm.getTitle());
        topic.setContent(topicForm.getContent());
        topic.setCategory(topicCategory);
        topic.set__V(0);
        topic.setCreatedAt(creationDate);
        topic.setUpdatedAt(creationDate);
        topic.setUser(user);
        topic.setViews(0);

        return topic;
    }

    public Map<String, Object> getJson(Topic topic, User user, List<Reply> replies) {
        Map<String, Object> json = new HashMap<>();

        json.put("category", topic.getCategory());
        json.put("content", topic.getContent());
        json.put("createdAt", topic.getCreatedAt());
        json.put("title", topic.getTitle());
        json.put("updatedAt", topic.getUpdatedAt());
        json.put("user", userBuilder.generateJsonFullInfo(user));
        json.put("views", topic.getViews());
        json.put("__v", topic.get__V());
        json.put("_id", topic.get_id());
        json.put("replies", replies);

        return json;
    }


    public List<Map<String, Object>> jsonFromList(List<Topic> topics) {
        List<Map<String, Object>> topicList = new ArrayList<>();
        for (Topic topic : topics) {
            topicList.add(createJsonForList(topic));
        }
        return topicList;
    }

    private Map<String, Object> createJsonForList(Topic topic) {
        Map<String, Object> topicJson = new HashMap<>();

        topicJson.put("category", topic.getCategory().get_id());
        topicJson.put("content", topic.getContent());
        topicJson.put("createdAt", topic.getCreatedAt());
        topicJson.put("id", topic.get_id());
        topicJson.put("_id", topic.get_id());
        topicJson.put("numberOfReplies", 0);
        topicJson.put("replies", null);
        topicJson.put("title", topic.getTitle());
        topicJson.put("updatedAt", topic.getUpdatedAt());
        topicJson.put("user", userBuilder.generateJson(topic.getUser()));
        topicJson.put("views", topic.getViews());
        topicJson.put("__v", topic.get__V());

        return topicJson;
    }

    public Map<String, Object> createJson(Topic topic, List<Reply> topicReplies) {
        Map<String, Object> topicJson = new HashMap<>();

        topicJson.put("category", topic.getCategory());
        topicJson.put("content", topic.getContent());
        topicJson.put("createdAt", topic.getCreatedAt());
        topicJson.put("id", topic.get_id());
        topicJson.put("_id", topic.get_id());
        topicJson.put("numberOfReplies", null);
        topicJson.put("replies", topicReplies);
        topicJson.put("title", topic.getTitle());
        topicJson.put("updatedAt", topic.getUpdatedAt());
        topicJson.put("user", userBuilder.generateJson(topic.getUser()));
        topicJson.put("views", topic.getViews());
        topicJson.put("__v", topic.get__V());

        return topicJson;
    }
}
