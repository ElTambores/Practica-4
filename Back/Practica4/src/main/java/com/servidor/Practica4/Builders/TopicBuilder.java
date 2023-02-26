package com.servidor.Practica4.Builders;

import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Topic;

public class TopicBuilder {
    public Topic fromForm(TopicForm topicForm) {
        Topic topic = new Topic();

        return topic;
    }
}
