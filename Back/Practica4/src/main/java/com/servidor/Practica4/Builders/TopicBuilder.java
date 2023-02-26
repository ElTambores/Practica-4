package com.servidor.Practica4.Builders;

import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;

import java.util.Date;

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
}
