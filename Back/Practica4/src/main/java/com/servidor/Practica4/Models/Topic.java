package com.servidor.Practica4.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long _id;

    String title;

    String content;

    Date createdAt;

    Date updatedAt;

    int views;

    int __V;

    @ManyToOne
    @JoinColumn(name = "user")
    User user;

    //Set<Reply> replies; añadir cuando se implementen las replies

    @ManyToOne
    @JoinColumn(name = "category")
    Category category;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int get__V() {
        return __V;
    }

    public void set__V(int __V) {
        this.__V = __V;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
