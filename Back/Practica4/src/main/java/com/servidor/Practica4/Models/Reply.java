package com.servidor.Practica4.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long _id;

    String content;

    Date createdAt;

    Date updatedAt;

    String __v;

    @ManyToOne
    @JoinColumn(name = "user")
    User user;

    @ManyToOne(targetEntity = Topic.class ,fetch = FetchType.LAZY)
    @JoinColumn(name = "topic", insertable = false, updatable = false)
    Topic topicFull;

    @Column (name = "topic")
    Long topic;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopicFull() {
        return topicFull;
    }

    public void setTopicFull(Topic topicFull) {
        setTopic(topicFull.get_id());
        this.topicFull = topicFull;
    }

    public Long getTopic() {
        return topic;
    }

    public void setTopic(Long topic) {
        this.topic = topic;
    }
}
