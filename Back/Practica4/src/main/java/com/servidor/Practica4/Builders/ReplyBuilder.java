package com.servidor.Practica4.Builders;

import com.servidor.Practica4.Forms.ReplyForm;
import com.servidor.Practica4.Models.Reply;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReplyBuilder {
    public Reply fromForm(ReplyForm replyForm, Topic topic, User creator) {
        Reply reply = new Reply();
        Date creationDate = new Date(System.currentTimeMillis());

        reply.setContent(replyForm.getContent());
        reply.setCreatedAt(creationDate);
        reply.setUpdatedAt(creationDate);
        reply.setTopic(topic);
        reply.setUser(creator);
        reply.set__v("0");

        return reply;
    }

    public Map<String, Object> getJson(Reply reply) {
        Map<String, Object> json = new HashMap<>();

        json.put("content", reply.getContent());
        json.put("createdAt", reply.getCreatedAt());
        json.put("topic", reply.getTopic().get_id());
        json.put("updatedAt", reply.getUpdatedAt());
        json.put("user", reply.getUser());
        json.put("__v", reply.get__v());
        json.put("_id", reply.get_id());

        return json;
    }
}
