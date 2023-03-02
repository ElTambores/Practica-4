package com.servidor.Practica4.Builders;

import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TopicBuilder {
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

    public Map<String, Object> getJson(Topic topic, User user) {
        Map<String, Object> json = new HashMap<>();

        json.put("category", topic.getCategory());
        json.put("content", topic.getContent());
        json.put("createdAt", topic.getCreatedAt());
        json.put("title", topic.getTitle());
        json.put("updatedAt", topic.getUpdatedAt());
        json.put("user", user);
        json.put("views", topic.getViews());
        json.put("__v", topic.get__V());
        json.put("_id", topic.get_id());

        //TODO arreglar recursividad de replies.
        json.put("replies", topic.getReplies());

        return json;
    }
}
