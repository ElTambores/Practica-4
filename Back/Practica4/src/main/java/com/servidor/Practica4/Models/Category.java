package com.servidor.Practica4.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long _id;

    String title;

    //slug es el titulo pero en minusculas y sustituir espacios por _
    //tiene que ser único (el titulo no). Si ya existe uno meter número random al final hasta que no lo sea.
    String slug;

    //Siempre 0 por ahora
    int __v;

    //Ejemplo color "hsl(11, 50%, 50%)"
    String color;

    String description;

    //A futuro debería ser una lista de usuarios resultando en una tabla pivote (ver ejemplo en portatil)
    String moderators;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModerators() {
        return moderators;
    }

    public void setModerators(String moderators) {
        this.moderators = moderators;
    }
}
